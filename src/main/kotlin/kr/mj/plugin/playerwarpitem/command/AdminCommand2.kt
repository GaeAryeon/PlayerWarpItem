package kr.mj.plugin.playerwarpitem.command

import kr.hqservice.framework.nms.extension.nms
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AdminCommand2 : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§c인게임에서 명령어를 입력해주세요.")
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage("§c좌표를 입력하세요")
            return false
        }

        // 0      1    2     3    4
        //"1.5" "2.5" "3.5" ".0" ".0"

        val doubleArguments =
            (if (args.size == 3) args + ".0" + ".0" else args)
                .mapNotNull {
                    // map -> Array , List , Collection ... 종류에서 사용할 수 있는 함수
                    //        뒤에 { } 괄호 안에서 끝나는 값으로 다시 매핑해서 리스트로 변환
                    it.toDoubleOrNull()
                }.filterIndexed { index, d ->
                    // filter -> Array , List , Collection ... 종류에서 사용할 수 있는 함수
                    //           뒤에 { } 괄호 안의 마지막 값이 true 인 애들만 구성해서 리스트로 변환

                    when (index) {
                        in 0..2 -> d < 999999 && d > -999999
                        else -> true
                    }
                }

        if (doubleArguments.size != 5) {
            sender.sendMessage("§c올바른 좌표값을 입력하세요")
            return false
        }

        val item = sender.inventory.itemInMainHand
        if (item.type == Material.AIR) {
            sender.sendMessage("§c손에 아이템이 없습니다.")
            return false
        }

        if (item.type == Material.ENDER_EYE || item.type == Material.ENDER_PEARL
            || item.type == Material.CHORUS_FRUIT || item.type == Material.WATER
            || item.type == Material.WATER_BUCKET || item.type == Material.LAVA || item.type == Material.LAVA_BUCKET || item.type.isEdible || item.type.isInteractable
        ) {
            sender.sendMessage("§c해당 아이템은 등록할 수 없습니다.")
            return false
        }

        if (item.type.isBlock) {
            sender.sendMessage("§c블럭은 등록할 수 없습니다.")
            return false
        }

        item.nms {
            tag {
                setDouble("X", doubleArguments[0])
                setDouble("Y", doubleArguments[1])
                setDouble("Z", doubleArguments[2])
                setFloat("DIRECTION1", doubleArguments[3].toFloat())
                setFloat("DIRECTION2", doubleArguments[4].toFloat())
            }
        }

        sender.sendMessage(("§e해당 아이템에 워프 좌표를 설정하였습니다 $doubleArguments"))
        return true
    }
}