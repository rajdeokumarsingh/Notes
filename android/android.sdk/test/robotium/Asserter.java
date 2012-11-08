
/**
 * Asserts that an expected {@link Activity} is currently active one.
 *
 * @param message the message that should be displayed if the assert fails
 * @param name the name of the {@code Activity} that is expected to be active e.g. {@code "MyActivity"}
 */
public void assertCurrentActivity(String message, String name)
    waiter.waitForActivity(name);
    Assert.assertEquals(message, name, activityUtils.getCurrentActivity().getClass().getSimpleName());

/**
 * Asserts that an expected {@link Activity} is currently active one.
 *
 * @param message the message that should be displayed if the assert fails
 * @param expectedClass the {@code Class} object that is expected to be active e.g. {@code MyActivity.class}
 *
 */
public void assertCurrentActivity(String message, Class<? extends Activity> expectedClass)

/**
 * Asserts that an expected {@link Activity} is currently active one, with the possibility to
 * verify that the expected {@code Activity} is a new instance of the {@code Activity}.
 * 
 * @param message the message that should be displayed if the assert fails
 * @param name the name of the {@code Activity} that is expected to be active e.g. {@code "MyActivity"}
 * @param isNewInstance {@code true} if the expected {@code Activity} is a new instance of the {@code Activity}
 * 
 */
public void assertCurrentActivity(String message, String name, boolean isNewInstance)


/**
 * Asserts that the available memory is not considered low by the system.
 * 
 */
public void assertMemoryNotLow()
{
    ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
    ((ActivityManager)activityUtils.getCurrentActivity().getSystemService("activity")).getMemoryInfo(mi);
    Assert.assertFalse("Low memory available: " + mi.availMem + " bytes", mi.lowMemory);
}
