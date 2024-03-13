import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

// PER_METHOD -> 객체 생성은 메소드마다 하나씩 붙음
// PER_CLASS  -> 객체 생성은 한 번만
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class TestCode {
    var number = 0

    @Test
    fun test1() {
        number++
        assertEquals(number, 1)
    }

    @Test
    fun test2() {
        number++
        assertEquals(number, 1)
    }
}