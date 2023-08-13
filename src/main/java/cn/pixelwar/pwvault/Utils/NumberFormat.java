package cn.pixelwar.pwvault.Utils;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;


public class NumberFormat {
    public String getIntFormat(int Num){
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Num);
    }
    public String getLongFormat(long Num){
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Num);
    }
     public String getFloatFormat(float Num){
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(Num);
    }
     public String getDoubleFormat(double Num){
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(Num);
    }

    public String getDecimalFormat(int decimal, double Num){
        BigDecimal b = new BigDecimal(Num);
        Num = b.setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(Num);
    }

    public static int getRandomInt(int min, int max){
        Random random  = new Random();
        return random.nextInt(max-min+1)+min;
    }
}
