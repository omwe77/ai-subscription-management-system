public abstract class AIModel {
    private String modelName;
    private double price;
    private int parameterCount;
    private int contextWindow;

    public AIModel(String modelName, double price, int parameterCount, int contextWindow) {
        this.modelName = modelName;
        this.price = price;
        this.parameterCount = parameterCount;
        this.contextWindow = contextWindow;
    }

    public String getModelName() 
    { 
        return modelName; 
    }

    public double getPrice() 
    {
        return price; 
    }

    public int getParameterCount()
    { 
        return parameterCount; 
    }

    public int getContextWindow()
    { 
        return contextWindow;
    }

    public int calculateTotalToken(String promptText, int expectedOutputTokens)
    {
        int inputTokens = promptText.trim().isEmpty() ? 0 : promptText.split("\\s+").length;
        int total = inputTokens + expectedOutputTokens;
        if (total > contextWindow) {
            throw new IllegalArgumentException(
                "Total tokens (" + total + ") exceed context window (" + contextWindow + ").");
        }
        return total;
    }

    public abstract String display();
}