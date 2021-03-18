package common

object Waiter {

    @Throws(Exception::class)
    fun wait(driver: Object, milliseconds: Long) {
        synchronized(driver) {
            driver.wait(milliseconds)
        }
    }
}