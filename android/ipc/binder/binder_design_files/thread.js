/*jslint evil:true */
/*global DISQUS:false */
/**
 * Dynamic thread loader
 *
 * 
 *  * 
 * 
*/

(function (window) {
    var DISQUS = window.DISQUS;
    var jsonData, cookieMessages, session;

    // 
    if (!DISQUS || typeof DISQUS === 'function') {
        throw "DISQUS object is not initialized";
    }
    // 
    // json_data and default_json django template variables will close
    // and re-open javascript comment tags

    /* */ jsonData = {"reactions": [], "reactions_limit": 10, "ordered_highlighted": [], "posts": {"554380520": {"edited": false, "author_is_moderator": false, "from_request_user": false, "up_voted": false, "can_edit": false, "ip": "", "last_modified_date": null, "dislikes": 0, "raw_message": "\u6b64\u6587\u725b\u903c", "has_replies": false, "vote": false, "votable": true, "last_modified_by": null, "real_date": "2012-06-11_10:14:33", "date": "7 \u6708\u524d", "message": "<p>\u6b64\u6587\u725b\u903c</p>", "approved": true, "is_last_child": false, "author_is_founder": false, "can_reply": true, "likes": 0, "user_voted": null, "num_replies": 0, "down_voted": false, "is_first_child": false, "has_been_anonymized": false, "highlighted": false, "parent_post_id": null, "depth": 0, "points": 0, "user_key": "google-5e56d39ec7e2966276cf74fe86e729a7", "author_is_creator": false, "email": "", "killed": false, "is_realtime": false}, "689765551": {"edited": false, "author_is_moderator": false, "from_request_user": null, "up_voted": false, "can_edit": false, "ip": "", "last_modified_date": null, "dislikes": 0, "raw_message": "\u4f5c\u8005\u725b\u903c,\u6587\u7ae0\u5199\u7684\u5f88\u597d", "has_replies": false, "vote": false, "votable": true, "last_modified_by": null, "real_date": "2012-10-23_01:38:45", "date": "3 \u6708\u524d", "message": "<p>\u4f5c\u8005\u725b\u903c,\u6587\u7ae0\u5199\u7684\u5f88\u597d</p>", "approved": true, "is_last_child": false, "author_is_founder": false, "can_reply": true, "likes": 0, "user_voted": null, "num_replies": 0, "down_voted": false, "is_first_child": false, "has_been_anonymized": false, "highlighted": false, "parent_post_id": null, "depth": 0, "points": 0, "user_key": "f248615a0a5b12b5de58755d9b6730bb", "author_is_creator": false, "email": "", "killed": false, "is_realtime": false}}, "ordered_posts": [689765551, 554380520], "realtime_enabled": false, "ready": true, "mediaembed": [], "has_more_reactions": false, "realtime_paused": false, "integration": {"receiver_url": "", "hide_user_votes": false, "reply_position": false, "disqus_logo": false}, "highlighted": {}, "reactions_start": 0, "media_url": "http://mediacdn.disqus.com/1359151009", "users": {"google-5e56d39ec7e2966276cf74fe86e729a7": {"username": "google-5e56d39ec7e2966276cf74fe86e729a7", "registered": true, "is_remote": true, "facebook": "", "verified": false, "about": "", "display_name": "Deng Joye", "url": "http://disqus.com/google-5e56d39ec7e2966276cf74fe86e729a7/", "remote_id": "5e56d39ec7e2966276cf74fe86e729a7", "blog": "", "points": 0, "avatar": "http://mediacdn.disqus.com/1359151009/images/noavatar32.png", "remote_domain": 6, "twitter": "", "remote_domain_name": "Google"}, "f248615a0a5b12b5de58755d9b6730bb": {"username": "Chenzheng110120", "registered": false, "is_remote": false, "facebook": "", "verified": false, "about": "", "display_name": "Chenzheng110120", "url": "http://disqus.com/guest/f248615a0a5b12b5de58755d9b6730bb/", "remote_id": null, "blog": "", "points": 0, "avatar": "http://mediacdn.disqus.com/1359151009/images/noavatar32.png", "remote_domain": "", "twitter": "", "remote_domain_name": ""}}, "user_unapproved": {}, "messagesx": {"count": 0, "unread": []}, "thread": {"voters_count": 0, "offset_posts": 0, "slug": "android_binder_8211", "likes": 19, "num_pages": 1, "days_alive": 0, "moderate_none": false, "voters": {}, "total_posts": 2, "realtime_paused": true, "queued": false, "pagination_type": "append", "user_vote": null, "num_posts": 2, "closed": false, "per_page": 5, "id": 241972518, "killed": false, "moderate_all": false}, "forum": {"use_media": true, "avatar_size": 32, "apiKey": "VJuJG9J774cSFO0fwC8y8h2XphEVS3Ug82npGOMMUNfcUA2xJYQ1L1zt0bHy0ePe", "features": {}, "comment_max_words": 0, "mobile_theme_disabled": false, "is_early_adopter": false, "login_buttons_enabled": true, "streaming_realtime": false, "reply_position": false, "id": 517143, "default_avatar_url": "http://mediacdn.disqus.com/1359151009/images/noavatar32.png", "template": {"url": "http://mediacdn.disqus.com/1359151009/uploads/themes/7884a9652e94555c70f96b6be63be216/theme.js?255", "mobile": {"url": "http://mediacdn.disqus.com/1359151009/uploads/themes/mobile/theme.js?254", "css": "http://mediacdn.disqus.com/1359151009/uploads/themes/mobile/theme.css?254"}, "api": "1.1", "name": "Houdini", "css": "http://mediacdn.disqus.com/1359151009/uploads/themes/7884a9652e94555c70f96b6be63be216/theme.css?255"}, "max_depth": 0, "ranks_enabled": false, "lastUpdate": "", "linkbacks_enabled": true, "allow_anon_votes": true, "revert_new_login_flow": false, "stylesUrl": "http://mediacdn.disqus.com/uploads/styles/51/7143/disanji.css", "show_avatar": true, "reactions_enabled": true, "disqus_auth_disabled": false, "name": "\u7b2c\u4e09\u6781", "language": "zh", "mentions_enabled": true, "url": "disanji", "allow_anon_post": true, "thread_votes_disabled": false, "hasCustomStyles": false, "moderate_all": false}, "settings": {"uploads_url": "http://media.disqus.com/uploads", "ssl_media_url": "https://securecdn.disqus.com/1359151009", "realtime_url": "http://rt.disqus.com/forums/realtime-cached.js", "facebook_app_id": "52254943976", "minify_js": true, "recaptcha_public_key": "6LdKMrwSAAAAAPPLVhQE9LPRW4LUSZb810_iaa8u", "read_only": false, "facebook_api_key": "52254943976", "juggler_url": "http://juggler.services.disqus.com", "debug": false, "disqus_url": "http://disqus.com", "media_url": "http://mediacdn.disqus.com/1359151009"}, "ranks": {}, "request": {"sort": "hot", "is_authenticated": false, "user_type": "anon", "subscribe_on_post": 0, "missing_perm": null, "user_id": null, "remote_domain_name": "", "remote_domain": "", "is_verified": false, "profile_url": "", "username": "", "is_global_moderator": false, "sharing": {}, "timestamp": "2013-01-26_21:29:02", "is_moderator": false, "ordered_unapproved_posts": [], "unapproved_posts": {}, "forum": "disanji", "is_initial_load": true, "display_username": "", "points": null, "has_email": false, "moderator_can_edit": false, "is_remote": false, "userkey": "", "page": 1}, "context": {"use_twitter_signin": false, "use_fb_connect": false, "show_reply": true, "sigma_chance": 10, "use_google_signin": false, "switches": {"listactivity_replies": true, "juggler_enabled": true, "promoted_discovery_budget": true, "next_realtime_indicators": true, "community_icon": true, "discovery_jones": true, "firehose": true, "promoted_click_optimization": true, "static_styles": true, "upload_media": true, "stats": true, "website_addons": true, "firehose_gnip_http": true, "textdigger_classification": true, "discovery_next": true, "show_captcha_on_links": true, "next_dragdrop_nag": true, "firehose_gnip": true, "firehose_pubsub": true, "dark_jester": true, "limit_get_posts_days_30d": true, "juggler_thread_onReady": true, "website_homepage_https_off": true, "promoted_discovery_random": true, "discovery_community": true, "phoenix": true, "phoenix_reputation": true, "discovery_next:top_placement": true, "uploads:UserProfile:s3_only": true, "shardpost:index": true, "new_sort_paginator": true, "use_rs_paginator_5m": true, "firehose_push": true, "enable_link_affiliation": true, "limit_textdigger": true, "textdigger_crawler": true, "phoenix_optout": true, "discovery_analytics": true, "discovery_next:truncate": true, "uploads:Forum:s3_only": true, "listactivity_replies_30d": true, "uploads:Forum": true, "uploads:UserProfile": true, "firehose_pubsub_throttle": true, "uploads": true, "next_discard_low_rep": true, "mentions": true, "shardpost": true}, "forum_facebook_key": "", "use_yahoo": false, "subscribed": false, "active_gargoyle_switches": ["community_icon", "dark_jester", "discovery_analytics", "discovery_community", "discovery_jones", "discovery_next", "discovery_next:top_placement", "discovery_next:truncate", "enable_link_affiliation", "firehose", "firehose_gnip", "firehose_gnip_http", "firehose_pubsub", "firehose_pubsub_throttle", "firehose_push", "juggler_enabled", "juggler_thread_onReady", "limit_get_posts_days_30d", "limit_textdigger", "listactivity_replies", "listactivity_replies_30d", "mentions", "new_sort_paginator", "next_discard_low_rep", "next_dragdrop_nag", "next_realtime_indicators", "phoenix", "phoenix_optout", "phoenix_reputation", "promoted_click_optimization", "promoted_discovery_budget", "promoted_discovery_random", "shardpost", "shardpost:index", "show_captcha_on_links", "static_styles", "stats", "textdigger_classification", "textdigger_crawler", "uploads", "uploads:Forum", "uploads:Forum:s3_only", "uploads:UserProfile", "uploads:UserProfile:s3_only", "use_rs_paginator_5m", "website_addons", "website_homepage_https_off"], "realtime_speed": 15000, "use_openid": false}}; /* */
    /* __extrajson__ */
    cookieMessages = {"user_created": null, "post_has_profile": null, "post_twitter": null, "post_not_approved": null}; session = {"url": null, "name": null, "email": null};

    DISQUS.jsonData = jsonData;
    DISQUS.jsonData.cookie_messages = cookieMessages;
    DISQUS.jsonData.session = session;

    if (DISQUS.useSSL) {
        DISQUS.useSSL(DISQUS.jsonData.settings);
    }

    // The mappings below are for backwards compatibility--before we port all the code that
    // accesses jsonData.settings to DISQUS.settings

    var mappings = {
        debug:                'disqus.debug',
        minify_js:            'disqus.minified',
        read_only:            'disqus.readonly',
        recaptcha_public_key: 'disqus.recaptcha.key',
        facebook_app_id:      'disqus.facebook.appId',
        facebook_api_key:     'disqus.facebook.apiKey'
    };

    var urlMappings = {
        disqus_url:    'disqus.urls.main',
        media_url:     'disqus.urls.media',
        ssl_media_url: 'disqus.urls.sslMedia',
        realtime_url:  'disqus.urls.realtime',
        uploads_url:   'disqus.urls.uploads'
    };

    if (DISQUS.jsonData.context.switches.realtime_setting_change) {
        urlMappings.realtimeHost = 'realtime.host';
        urlMappings.realtimePort = 'realtime.port';
    }
    for (key in mappings) {
        if (mappings.hasOwnProperty(key)) {
            DISQUS.settings.set(mappings[key], DISQUS.jsonData.settings[key]);
        }
    }

    for (key in urlMappings) {
        if (urlMappings.hasOwnProperty(key)) {
            DISQUS.jsonData.settings[key] = DISQUS.settings.get(urlMappings[key]);
        }
    }

    DISQUS.jsonData.context.csrf_token = '21bc467119200cb06806902fa8e2f5b0';

    DISQUS.jsonData.urls = {
        login: 'http://disqus.com/profile/login/',
        logout: 'http://disqus.com/logout/',
        upload_remove: 'http://disanji.disqus.com/thread/android_binder_8211/async_media_remove/',
        request_user_profile: 'http://disqus.com/AnonymousUser/',
        request_user_avatar: 'http://mediacdn.disqus.com/1359151009/images/noavatar92.png',
        verify_email: 'http://disqus.com/verify/',
        remote_settings: 'http://disanji.disqus.com/_auth/embed/remote_settings/',
        edit_profile_window: 'http://disqus.com/embed/profile/edit/',
        embed_thread: 'http://disanji.disqus.com/thread.js',
        embed_vote: 'http://disanji.disqus.com/vote.js',
        embed_thread_vote: 'http://disanji.disqus.com/thread_vote.js',
        embed_thread_share: 'http://disanji.disqus.com/thread_share.js',
        embed_queueurl: 'http://disanji.disqus.com/queueurl.js',
        embed_hidereaction: 'http://disanji.disqus.com/hidereaction.js',
        embed_more_reactions: 'http://disanji.disqus.com/more_reactions.js',
        embed_subscribe: 'http://disanji.disqus.com/subscribe.js',
        embed_highlight: 'http://disanji.disqus.com/highlight.js',
        embed_block: 'http://disanji.disqus.com/block.js',
        update_moderate_all: 'http://disanji.disqus.com/update_moderate_all.js',
        update_days_alive: 'http://disanji.disqus.com/update_days_alive.js',
        show_user_votes: 'http://disanji.disqus.com/show_user_votes.js',
        forum_view: 'http://disanji.disqus.com/android_binder_8211',
        cnn_saml_try: 'http://disqus.com/saml/cnn/try/',
        realtime: DISQUS.jsonData.settings.realtime_url,
        thread_view: 'http://disanji.disqus.com/thread/android_binder_8211/',
        twitter_connect: DISQUS.jsonData.settings.disqus_url + '/_ax/twitter/begin/',
        yahoo_connect: DISQUS.jsonData.settings.disqus_url + '/_ax/yahoo/begin/',
        openid_connect: DISQUS.jsonData.settings.disqus_url + '/_ax/openid/begin/',
        googleConnect: DISQUS.jsonData.settings.disqus_url + '/_ax/google/begin/',
        community: 'http://disanji.disqus.com/community.html',
        admin: 'http://disanji.disqus.com/admin/moderate/',
        moderate: 'http://disanji.disqus.com/admin/moderate/',
        moderate_threads: 'http://disanji.disqus.com/admin/moderate-threads/',
        settings: 'http://disanji.disqus.com/admin/settings/',
        unmerged_profiles: 'http://disqus.com/embed/profile/unmerged_profiles/',
        juggler: DISQUS.jsonData.settings.juggler_url,

        channels: {
            def:      'http://disqus.com/default.html', /* default channel */
            auth:     'https://disqus.com/embed/login.html',
            tweetbox: 'http://disqus.com/forums/integrations/twitter/tweetbox.html?f=disanji',
            edit:     'http://disanji.disqus.com/embed/editcomment.html'
        }
    };


    // 
    //     
    DISQUS.jsonData.urls.channels.reply = 'http://mediacdn.disqus.com/1359151009/build/system/reply.html';
    DISQUS.jsonData.urls.channels.upload = 'http://mediacdn.disqus.com/1359151009/build/system/upload.html';
    DISQUS.jsonData.urls.channels.sso = 'http://mediacdn.disqus.com/1359151009/build/system/sso.html';
    DISQUS.jsonData.urls.channels.facebook = 'http://mediacdn.disqus.com/1359151009/build/system/facebook.html';
    //     
    // 
}(window));
