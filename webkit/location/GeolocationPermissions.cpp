
// 数据库
static const char* databaseName = "GeolocationPermissions.db";


// 保存所有的GeolocationPermissions对象的vector
GeolocationPermissions::GeolocationPermissionsVector GeolocationPermissions::s_instances;

// permanent permission
GeolocationPermissions::PermissionsMap GeolocationPermissions::s_permanentPermissions;
// temporary permission
PermissionsMap m_temporaryPermissions;

bool GeolocationPermissions::s_alwaysDeny = false;
bool GeolocationPermissions::s_permanentPermissionsLoaded = false;
bool GeolocationPermissions::s_permanentPermissionsModified = false;
String GeolocationPermissions::s_databasePath;


GeolocationPermissions::GeolocationPermissions(WebViewCore* webViewCore, Frame* mainFrame)
: m_webViewCore(webViewCore)
, m_mainFrame(mainFrame)
  , m_timer(this, &GeolocationPermissions::timerFired)
{
    maybeLoadPermanentPermissions();
    // 加入全局vector
    s_instances.append(this);
}

GeolocationPermissions::~GeolocationPermissions()
{
    // 从全局vector中移除
    size_t index = s_instances.find(this);
    s_instances.remove(index);
}

// 查询权限状态
void GeolocationPermissions::queryPermissionState(Frame* frame)
{
    // We use SecurityOrigin::toString to key the map. Note that testing
    // the SecurityOrigin pointer for equality is insufficient.
    String originString = frame->document()->securityOrigin()->toString();

    // If we've been told to always deny requests, do so.
    if (s_alwaysDeny) {
        makeAsynchronousCallbackToGeolocation(originString, false);
        return;
    }

    // Check the permanent permisions.
    // looking for originString in s_permanentPermissions map
    if(found) 
        makeAsynchronousCallbackToGeolocation(originString, allow);
        return;

    // Check the temporary permisions.
    // looking for originString in s_permanentPermissions map
    if (found)
        makeAsynchronousCallbackToGeolocation(originString, allow);
        return;

    // If there's no pending request, prompt the user.
    if (nextOriginInQueue().isEmpty()) {
        // Although multiple tabs may request permissions for the same origin
        // simultaneously, the routing in WebViewCore/CallbackProxy ensures that
        // the result of the request will make it back to this object, so
        // there's no need for a globally unique ID for the request.
        m_webViewCore->geolocationPermissionsShowPrompt(originString);
    }

    // Add this request to the queue so we can track which frames requested it.
    if (m_queuedOrigins.find(originString) == WTF::notFound) {
        m_queuedOrigins.append(originString);
        FrameSet frameSet;
        frameSet.add(frame);
        m_queuedOriginsToFramesMap.add(originString, frameSet);
    } else {
        ASSERT(m_queuedOriginsToFramesMap.contains(originString));
        m_queuedOriginsToFramesMap.find(originString)->second.add(frame);
    }
}



