import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Map {
    // 5 개의 실수형 값이 필요한 상태 하지만 값은 문자열로 명령어로 받음.
    // 해당 값을 맵(함수)을 통해 바꾸는 과정 테스트
    private val okayArguments = arrayOf("0.5", "2.5", "1.5", "1", "2.5")
    private val badArguments1 = arrayOf("0.5", "2.5", "1.5", "r", "1.5")
    private val badArguments2 = arrayOf("9999999", "2.5", "1.5", "1.5", "2.45")

    // map -> Array , List , Collection ... 종류에서 사용할 수 있는 함수
    //        뒤에 { } 괄호 안에서 끝나는 값으로 다시 매핑해서 리스트로 변환

    // filter -> Array , List , Collection ... 종류에서 사용할 수 있는 함수
    //        뒤에 { } 괄호 안에서 마지막으로 설정 된 값이 true 면 해당 값을 리스트에 넣고
    //        값이 false 면 해당 값을 빼서 다시 리스트로 변환

    @Test
    fun map_test1() {
        val map = okayArguments.mapNotNull {
            it.toDoubleOrNull()
        }

        assertEquals(map.size, 5)
    }

    @Test
    fun map_test2() {
        val map = badArguments1.mapNotNull {
            it.toDoubleOrNull()
        }

        assertNotEquals(map.size, 5)
    }

    @Test
    fun map_test3() {
        val map = badArguments2.mapNotNull {
            it.toDoubleOrNull()
        }.filterIndexed { index, d ->

            when (index) {
                in 0..2 -> d < 999999 && d > -999999
                else -> true
            }
        }

        assertNotEquals(map.size, 5)
    }
}