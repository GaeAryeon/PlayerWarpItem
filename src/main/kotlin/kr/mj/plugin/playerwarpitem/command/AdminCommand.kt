package kr.mj.plugin.playerwarpitem.command

import kr.hqservice.framework.nms.extension.nms
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AdminCommand : CommandExecutor {

    private val materials = listOf(
        Material.ENDER_EYE,
        Material.ENDER_PEARL,
        Material.CHORUS_FRUIT,
        Material.WATER,
        Material.WATER_BUCKET,
        Material.LAVA,
        Material.LAVA_BUCKET
    )

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("§c인게임에서 명령어를 입력해주세요.")
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage("§c좌표를 입력하세요")
            return false
        }
        val coordinateX = args[0].toDoubleOrNull()
        val coordinateY = args[1].toDoubleOrNull()
        val coordinateZ = args[2].toDoubleOrNull()
        val direction1 = args[3].toDouble()
        val direction2 = args[4].toDouble()

        if (coordinateX == null || coordinateY == null || coordinateZ == null || coordinateX > 999999 || coordinateX < -999999 || coordinateY > 999999 || coordinateY < -999999 || coordinateZ > 999999 || coordinateZ < -999999) {
            sender.sendMessage("§c올바른 좌표값을 입력하세요")
            return false
        }
        val itemStack = sender.inventory.itemInMainHand
        val material = itemStack.type
        if (material == Material.AIR) {
            sender.sendMessage("§c손에 아이템이 없습니다.")
            return false
        }
        if (materials.contains(material) || material.isEdible || material.isInteractable || material.isBlock) {
            sender.sendMessage("§c해당 아이템은 등록할 수 없습니다.")
            return false
        }
        itemStack.nms {
            tag {
                setDouble("X", coordinateX)
                setDouble("Y", coordinateY)
                setDouble("Z", coordinateZ)
                setDouble("DIRECTION1", direction1)
                setDouble("DIRECTION2", direction2)
            }
        }
        sender.sendMessage(("§e해당 아이템에 워프 좌표를 설정하였습니다 [$coordinateX, $coordinateY, $coordinateZ] [$direction1, $direction2]"))
        return true
    }
}