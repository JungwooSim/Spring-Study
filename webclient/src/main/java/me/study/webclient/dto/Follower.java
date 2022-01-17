package me.study.webclient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Follower {
    public Follower(Follower follower) {
        this.siteAdmin = follower.isSiteAdmin();
//        this.type = follower.getType();
//        this.receivedEventsUrl = follower.getReceivedEventsUrl();
//        this.eventsUrl = follower.getEventsUrl();
//        this.reposUrl = follower.getReposUrl();
//        this.organizationsUrl = follower.getOrganizationsUrl();
//        this.subscriptionsUrl = follower.getSubscriptionsUrl();
//        this.starredUrl = follower.getStarredUrl();
//        this.gistsUrl = follower.getGistsUrl();
//        this.followingUrl = follower.getFollowingUrl();
//        this.followersUrl = follower.getFollowersUrl();
//        this.htmlUrl = follower.getHtmlUrl();
//        this.url = follower.getUrl();
//        this.gravatarId = follower.getGravatarId();
//        this.avatarUrl = follower.getAvatarUrl();
//        this.nodeId = follower.getNodeId();
//        this.id = follower.getId();
//        this.login = follower.getLogin();
    }

    @JsonProperty("site_admin")
    private boolean siteAdmin;
    @JsonProperty("type")
    private String type;
    @JsonProperty("received_events_url")
    private String receivedEventsUrl;
    @JsonProperty("events_url")
    private String eventsUrl;
    @JsonProperty("repos_url")
    private String reposUrl;
    @JsonProperty("organizations_url")
    private String organizationsUrl;
    @JsonProperty("subscriptions_url")
    private String subscriptionsUrl;
    @JsonProperty("starred_url")
    private String starredUrl;
    @JsonProperty("gists_url")
    private String gistsUrl;
    @JsonProperty("following_url")
    private String followingUrl;
    @JsonProperty("followers_url")
    private String followersUrl;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("url")
    private String url;
    @JsonProperty("gravatar_id")
    private String gravatarId;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("node_id")
    private String nodeId;
    @JsonProperty("id")
    private int id;
    @JsonProperty("login")
    private String login;
}
