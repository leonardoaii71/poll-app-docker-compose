package practica13.Models;

import java.util.List;

public class EncuestaResponse {

    public String label;
    public int rate;

    public EncuestaResponse(String label, int rate) {
        this.label = label;
        this.rate = rate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
