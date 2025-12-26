import java.util.*;

/*出局紀錄與行為日誌 (History Logger)追蹤玩家動作，紀錄出局瞬間的具體原因，並在結算時產出摘要*/
public class DeathHistoryLogger {

    // 儲存格式：Key 為 PlayerID, Value 為出局原因描述 [cite: 168, 755]
    private Map<String, String> deathNotes = new HashMap<>();

    /**
    紀錄玩家出局的具體原因
    CallNumberCommand 偵測到玩家踩到陷阱時會呼叫此方法 [cite: 172, 748, 780]
    @param playerId 出局玩家的 ID
    @param trapNumber 踩到的陷阱數字
    @param roundCount 目前遊戲喊數的進度或輪數 [cite: 761, 762]
    */

    public void recordElimination(String playerId, int trapNumber, int roundCount) {
        // 生成描述性字串，例如：「在第 5 輪喊到 8，不幸踩中陷阱！」 [cite: 167, 762]
        String reason = "喊到數字 " + trapNumber + "，是陷阱數字！";
        deathNotes.put(playerId, reason);
        
        // 在伺服器控制台打印日誌，方便除錯 [cite: 764]
        System.out.println("[Logger] 紀錄出局：" + playerId + " - " + reason);
    }

    /**
     * 產出所有出局者的摘要字串，格式符合協定規範
     * * @return 格式範例："A1113368:原因1;A1113207:原因2;" [cite: 186, 773]
     */

    public String getAllDeathReasons() {
        if (deathNotes.isEmpty()) {
            return "無人出局"; // [cite: 770]
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : deathNotes.entrySet()) {
            
            sb.append(entry.getKey())
              .append(":")
              .append(entry.getValue())
              .append(";");
        }
        return sb.toString();
    }
    
    //清除紀錄（用於重新開始遊戲時）
    
    public void clearLogs() {
        deathNotes.clear();
    }
}