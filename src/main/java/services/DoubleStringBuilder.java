package services;

import com.sun.javafx.binding.StringFormatter;

public class DoubleStringBuilder {
    private final StringBuilder stringBuilder;

    public DoubleStringBuilder(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public DoubleStringBuilder append(double d){
        return new DoubleStringBuilder(stringBuilder.append(StringFormatter.format("%.4f", d).get().replace(",",".")));
    }

    public DoubleStringBuilder append(String str){
        return new DoubleStringBuilder(stringBuilder.append(str));
    }

    public DoubleStringBuilder endl(){
        return new DoubleStringBuilder(stringBuilder.append("\r\n"));
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
