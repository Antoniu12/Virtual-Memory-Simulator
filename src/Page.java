public class Page {
    private String nume;
    public Page(String nume){
        this.nume=nume;
    }

    public String getNume(){
        return this.nume;
    }

    public boolean equals(Page p) {
        return this.nume.equals(p.getNume());
    }

    @Override
    public String toString() {
        return this.nume;
    }
}
