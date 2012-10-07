#if 0
Key layout文件在目录
    device/xmm6181-ts-256mb/android/*.kl
    key 399   GRAVE
    key 2     1
    key 3     2
    key 4     3
    key 5     4
    key 6     5
    key 7     6
    key 8     7
    key 9     8
    key 10    9
    key 11    0
    key 61    BACK              WAKE_DROPPED
    key 230   SOFT_RIGHT        WAKE
    key 107   DPAD_CENTER       WAKE_DROPPED
    ...
#endif

// 用来扫描*.kl文件
// key 230(scan code)   SOFT_RIGHT(key code)        WAKE(flag)
// key code     按键的代码/名称
// scan code    硬件产生的扫描码
class KeyLayoutMap
    status_t load(const char* filename);

    status_t map(int32_t scancode, int32_t *keycode, uint32_t *flags) const;
    status_t findScancodes(int32_t keycode, Vector<int32_t>* outScancodes) const;

    struct Key {
        int32_t keycode;
        uint32_t flags;
    };

    status_t m_status;
    KeyedVector<int32_t,Key> m_keys;

    // 扫描*.kl文件， 生成m_keys数组
    // *.kl 文件形如:
    // key 230(scan code) SOFT_RIGHT(key code) WAKE(flag)
    status_t KeyLayoutMap::load(const char* filename)

        // key code通过下面数组转化为int类型
        base/include/ui/KeycodeLabels.h
        static const KeycodeLabel KEYCODES[] = {
            { "SOFT_LEFT", 1 },
            { "SOFT_RIGHT", 2 },
            { "HOME", 3 },  
            { "BACK", 4 },      
            { "CALL", 5 },      
            { "ENDCALL", 6 },   
            { "0", 7 },         
            { "1", 8 },         
            ...

        // flags 通过下面数组转化为int类型
        static const KeycodeLabel FLAGS[] = {
            { "WAKE", 0x00000001 },
            { "WAKE_DROPPED", 0x00000002 },
            { "SHIFT", 0x00000004 },
            { "CAPS_LOCK", 0x00000008 },
            { "ALT", 0x00000010 },
            { "ALT_GR", 0x00000020 },
            { "MENU", 0x00000040 },
            { "LAUNCHER", 0x00000080 },
            { NULL, 0 }

    // 通过scancode获取keycode和flags
    status_t KeyLayoutMap::map(int32_t scancode, int32_t *keycode, uint32_t *flags) const

    // 通过keycode获取scancode
    status_t KeyLayoutMap::findScancodes(int32_t keycode, Vector<int32_t>* outScancodes) const







