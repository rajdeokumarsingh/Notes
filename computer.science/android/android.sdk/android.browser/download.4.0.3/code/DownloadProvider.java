
sqlite> .table
android_metadata  downloads         request_headers 
sqlite> select * from request_headers;
   id          download_id  header      value                                      
   ----------  -----------  ----------  -------------------------------------------
   24          24           cookie      JSESSIONID=C8B1F8DF795F776C51DDB493C404C7C9
   25          25           cookie                                                 
   26          26           cookie                                                 
   27          27           cookie                                                 
   28          28           cookie      BAIDUID=3AB6D30D7EA010AB330FC340D83BFCA8:FG
   29          29           cookie                                                 
   30          30           cookie                                                 
   31          31           cookie 


sqlite> select * from downloads;
_id         uri                                                              method      entity      no_integrity  hint                                            otaupdate   _data                                    mimetype      destination  no_system   visibility  control     status      numfailed   lastmod        notificationpackage  notificationclass  notificationextras  cookiedata  useragent   referer     total_bytes  current_bytes  etag                       uid         otheruid    title         description      scanned     is_public_api  allow_roaming  allowed_network_types  is_visible_in_downloads_ui  bypass_recommended_size_limit  mediaprovider_uri                          deleted     errorMsg  
----------  ---------------------------------------------------------------  ----------  ----------  ------------  ----------------------------------------------  ----------  ---------------------------------------  ------------  -----------  ----------  ----------  ----------  ----------  ----------  -------------  -------------------  -----------------  ------------------  ----------  ----------  ----------  -----------  -------------  -------------------------  ----------  ----------  ------------  ---------------  ----------  -------------  -------------  ---------------------  --------------------------  -----------------------------  -----------------------------------------  ----------  ----------
24          http://218.206.177.209:8080/waptest/browser15/file/testfile.mp3  0                                     file:///mnt/sdcard/Download/music/testfile.mp3              /mnt/sdcard/Download/music/testfile.mp3  audio/x-mpeg  4                        0                       200         0           1333966654175  com.android.browser                                                                             1175846      1175846        W/"1175846-1290741852000"  10004                   testfile.mp3  218.206.177.209  1           1              1              8                      1                           0                              content://media/external/audio/media/1116  0                     
25          http://cdn1.down.apk.gfan.com/asdf/Pfiles/2012/3/30/154682_bc2d  0                                     file:///mnt/sdcard/Download/154682_bc2d3084-74              /mnt/sdcard/Download/154682_bc2d3084-74  application/  4                        0                       200         0           1333979977837  com.android.browser                                                                             1222389      1222389        "2106505100"               10004                   154682_bc2d3  cdn1.down.apk.g  1           1              1              2                      1                           0                              content://media/external/file/1117         0      
