package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polynomial {

    private List<Integer> coefficients;

    private final int degree;

    public Polynomial(List<Integer> coefficients){
        this.coefficients = coefficients;
        this.degree = coefficients.size() - 1;
    }

    public Polynomial(int degree){
        this.degree = degree;
        this.generateCoefficients(degree);
    }

    public void generateCoefficients(int degree){
        coefficients = new ArrayList<>();
        Random r = new Random();
        for(int i = 0; i < degree; i++){
            coefficients.add(1);
        }
        coefficients.add(1);
    }

    public List<Integer> getCoefficients(){
        return this.coefficients;
    }

    public int getDegree(){
        return this.degree;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(coefficients.get(degree)).append("x^").append(degree);

        for(int i = degree - 1; i > 0; i--){
            if(coefficients.get(i) != 0){
                builder.append("+").append(coefficients.get(i)).append("x^").append(i);
            }
        }
        if(coefficients.get(0) != 0){
            builder.append("+").append(coefficients.get(0));
        }

        return builder.toString();
    }
}
