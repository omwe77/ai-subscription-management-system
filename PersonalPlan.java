/**
 * Personal plan for individual users. Tracks available tokens.
 * 
 * @author Om Dangol
 * @version Final
 */
public class PersonalPlan extends AIModel {
    private int availableTokens;

    public PersonalPlan(String modelName, double price, int parameterCount,int contextWindow, int availableTokens) {
        super(modelName, price, parameterCount, contextWindow);
        this.availableTokens = availableTokens;
    }

    public int getAvailableTokens() {
        return availableTokens;
    }

    public String buyTokens(int extraTokens) {
        if (extraTokens <= 0) {
            return "Enter a positive number of tokens.";
        }
        availableTokens += extraTokens;
        return "Tokens added. New balance: " + availableTokens;
    }

    public String usePrompt(String promptText, int expectedOutputTokens) {
        try {
            int total = calculateTotalToken(promptText, expectedOutputTokens);
            if (availableTokens < total) {
                return "Insufficient tokens. Available: " + availableTokens + ", Required: " + total;
            }
            availableTokens -= total;
            return "Prompt accepted. Tokens used: " + total + ", Remaining: " + availableTokens;
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
        "\nAvailable Tokens: " + availableTokens + "\n";
    }
}
