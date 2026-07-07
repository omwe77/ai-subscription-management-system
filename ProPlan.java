/**
 * Pro plan for teams. Uses a fixed-size array to store member names
 * for search purposes. Slots are limited; name array prevents
 * adding beyond capacity and supports removal/search.
 */
public class ProPlan extends AIModel {
    private int totalSlots;
    private String[] members;      // plain array for names 
    private int memberCount;

    public ProPlan(String modelName, double price, int parameterCount,int contextWindow, int teamSlots) {
        super(modelName, price, parameterCount, contextWindow);
        this.totalSlots = teamSlots;
        members = new String[totalSlots];
        memberCount = 0;
    }

    public int getTeamSlots() {
        return totalSlots - memberCount;
    }

    public String addMember(String name) {
        if (memberCount >= totalSlots) {
            return "No slots available.";
        }
        members[memberCount] = name;
        memberCount++;
        return name + " added. Slots left: " + getTeamSlots();
    }

    public String removeMember(String name) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].equalsIgnoreCase(name)) 
            {
                // Shift remaining names left
                for (int j = i; j < memberCount - 1; j++)
                {
                    members[j] = members[j + 1];
                }
                members[memberCount - 1] = null;
                memberCount--;
                return name + " removed. Slots left: " + getTeamSlots();
            }
        }
        return name + " is not a member of this team.";
    }

    // Checks if a name exists in this team (case-insensitive). 
    public boolean hasMember(String name) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public String usePrompt(String promptText, int expectedOutputTokens) {
        try {
            int total = calculateTotalToken(promptText, expectedOutputTokens);
            return "Prompt accepted (Pro plan – no deduction). Total tokens: " + total;
        } catch (IllegalArgumentException e) {
            return "Prompt rejected: " + e.getMessage();
        }
    }

    @Override
    public String display() {
        return "Model: " + getModelName() +
               "\nPrice (NPR/1L): " + getPrice() +
               "\nParameters: " + getParameterCount() + "B" +
               "\nContext Window: " + getContextWindow() + " tokens" +
               "\nTeam Slots Available: " + getTeamSlots() + "\n";
    }
}