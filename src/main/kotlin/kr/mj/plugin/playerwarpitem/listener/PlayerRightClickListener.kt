package kr.mj.plugin.playerwarpitem.listener

import kr.hqservice.framework.nms.extension.getNmsItemStack
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class PlayerRightClickListener : Listener {
    @EventHandler
    fun rightClickWrap(event: PlayerInteractEvent) {
        // 플레이어가 우클릭, 좌클릭, 발판 밟기
        // 우클릭 ( 1.12 + -> 왼손 등장! )
        // 왼손에 블록이 있을 수 있음, 오른손에도 블록있을 수 있음...
        // 그래서 마인크래프트는 어느손을 우선시 하느냐 ? ( 오른손 )
        //  -> 왼손에만 블록이 있고 오른손에는 검이 있는 경우
        //  -> 점프 우클릭을 바닥에 했다 ? -> (왼손에 있는) 블록이 설치돼요.
        //  -> 근데 ? 오른손에도[양손] 블록이 있는 경우 -> (오른손에 있는) 블록이 설치 됨.
        // -> 우클릭 하면 ? 오른손 먼저 체크하고 왼손도 체크한다는 의미
        // -> 즉, 두 번 이 이벤트가 실행됨을 의미.
        // -> 좌클릭은 한 번만 무조건 오른손에 있는 아이템을 휘두름

        if (event.hand != EquipmentSlot.HAND) return
        // 위 조건문을 통해 이 이벤트가 두 번 실행되는 것을 방지
        if (event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR) {

            val player = event.player

            val wrapItem = player.inventory.itemInMainHand
            if (wrapItem.type == Material.AIR) return

            val tag = wrapItem.getNmsItemStack().getTagOrNull() ?: return
            if (!tag.hasKey("X") && !tag.hasKey("Y") && !tag.hasKey("Z")) return

            val coordinateX = tag.getDouble("X")
            val coordinateY = tag.getDouble("Y")
            val coordinateZ = tag.getDouble("Z")
            val direction1 = tag.getFloat("DIRECTION1")
            val direction2 = tag.getFloat("DIRECTION2")

            val loc = Location(Bukkit.getWorld("world"), coordinateX, coordinateY, coordinateZ, direction1, direction2)

            val block = player.inventory.itemInMainHand
            if (block.type.isItem) {
                player.teleport(loc)
                wrapItem.amount--

                player.sendMessage("§a해당 위치로 이동 하였습니다.")
            }
        }
    }
}