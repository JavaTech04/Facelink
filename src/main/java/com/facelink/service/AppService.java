package com.facelink.service;

import com.facelink.dto.CustomUser;
import com.facelink.entity.*;
import com.facelink.enums.PostAudience;
import com.facelink.enums.TypePost;
import com.facelink.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class AppService {
    @Autowired
    private ListFriendsRepository listFriendsRepository;

    @Autowired
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RelationShipRepository relationShipRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    public AccountInfo getInfo() {
        return this.accountInfoRepository.getBio(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
    }

    public Set<?> getFriends(Long id) {
        return this.listFriendsRepository.getFriendByUser(id);
    }

    public List<?> getListFriends() {
        return this.accountInfoRepository.getAccountInfoByAccountId(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
    }

    public ListFriend hasFriend(Long userId) {
        List<ListFriend> listFriend = this.listFriendsRepository.hasFriend(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), userId);
        return listFriend.isEmpty() ? ListFriend.builder().status(-1).build() : listFriend.getFirst();
    }

    @Transactional
    public void unfriend(Long userId) {
        this.listFriendsRepository.removeFriend(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), userId);
    }

    @Transactional
    public void cancelFriend(Long userId) {
        this.listFriendsRepository.removeFriend(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), userId);
    }

    @Transactional
    public void senRequestAddFriend(Long userId) {
        this.listFriendsRepository.save(ListFriend.builder()
                .accountId(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId())
                .friendInfo(this.accountRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found account!!")))
                .status(0)
                .build());
    }

    @Transactional
    public void confirmFriend(Long userId) {
        this.listFriendsRepository.updateStatusFriend(userId);
        this.listFriendsRepository.save(ListFriend.builder()
                .accountId(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId())
                .friendInfo(this.accountRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Not found account!!")))
                .status(1)
                .build());
    }

    public List<?> getFriendRequests() {
        return this.listFriendsRepository.friendRequests(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId());
    }

    public List<Role> getRoles() {
        return this.roleRepository.findAll();
    }

    public List<?> getRelationships() {
        return this.relationShipRepository.findAll();
    }

    @Transactional
    public void editDetails(AccountInfo accountInfo) {
        Account account = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
        accountInfo.setAccount(account);
        accountInfo.setBio(account.getAccountInfo().getBio());
        accountInfo.setCoverPhoto(account.getAccountInfo().getCoverPhoto());
        accountInfo.setAvatar(account.getAccountInfo().getAvatar());
        accountInfo.setFirstName(account.getAccountInfo().getFirstName());
        accountInfo.setLastName(account.getAccountInfo().getLastName());
        accountInfo.setFullName(account.getAccountInfo().getFullName());
        this.accountInfoRepository.save(accountInfo);
    }

    public void updateBio(String bio) {
        Account account = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
        this.accountInfoRepository.updateBio(account.getAccountInfo().getId(), bio);
    }

    public void updateAvatar(String url) {
        Account a = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
        this.accountInfoRepository.updateAvatar(a.getAccountInfo().getId(), url);

        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = customUser.getAccount();
        account.getAccountInfo().setAvatar(url);
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                customUser, customUser.getPassword(), customUser.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(newAuthentication);
        SecurityContextHolder.setContext(context);
    }

    public void updateCoverPhoto(String url) {
        Account a = ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
        this.accountInfoRepository.updateCoverPhoto(a.getAccountInfo().getId(), url);

        CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = customUser.getAccount();
        account.getAccountInfo().setCoverPhoto(url);
        UsernamePasswordAuthenticationToken newAuthentication = new UsernamePasswordAuthenticationToken(
                customUser, customUser.getPassword(), customUser.getAuthorities());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(newAuthentication);
        SecurityContextHolder.setContext(context);
    }

    public void postNew(Post getPost) {
        Post post = Post.builder()
                .type(getPost.getUrlImage().isBlank() && getPost.getUrlVideo().isBlank() ? TypePost.CONTENT : getPost.getUrlImage().isBlank() ? TypePost.CONTENT_VIDEO : TypePost.CONTENT_IMAGE)
                .content(getPost.getContent())
                .urlImage(getPost.getUrlImage())
                .urlVideo(getPost.getUrlVideo())
                .postAudience(PostAudience.PUBLIC)
                .createDate(LocalDateTime.now())
                .account(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount())
                .build();
        this.postRepository.save(post);
    }

    public Page<?> getPostsProfile(Long id, Pageable pageable) {
        return this.postRepository.getPostByAccount(id, pageable);
    }

    public Page<?> getPostsPublic(Pageable pageable) {
        return this.postRepository.getPostsPublic(PostAudience.PUBLIC, pageable);
    }

    @Transactional
    public void deletePost(Long id) {
        this.reactionRepository.deleteReactionByPost(id);
        this.commentRepository.deleteCommentByPost(id);
        this.postRepository.deleteById(id);
    }

    public Post findById(Long id) {
        return this.postRepository.findById(id).orElse(null);
    }

    public void updatePostContent(String content, Long id) {
        this.postRepository.updatePost(id, content);
    }

    public void createComment(String content, Long postId) {
        Comment comment = Comment.builder()
                .content(content)
                .account(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount())
                .post(this.findById(postId))
                .createDate(new Date())
                .build();
        this.commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        this.commentRepository.deleteById(id);
    }

    public boolean hasLike(Long idPost) {
        return this.reactionRepository.hasLike(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), idPost) > 0;
    }

    public void likeAndUnlike(Long idPost, String type) {
        if (this.reactionRepository.hasLike(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), idPost) > 0) {
            this.reactionRepository.unlike(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), idPost);
        } else {
            Reaction reaction = Reaction.builder()
                    .post(this.findById(idPost))
                    .account(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount())
                    .type(type)
                    .build();
            this.reactionRepository.save(reaction);
        }
    }

    public String getReactionType(Long idPost){
        return this.reactionRepository.getReactionType(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount().getId(), idPost);
    }
}
