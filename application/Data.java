package application;

/**
   * A class to store the a date-weight pair
   * @author Kexin Chen
   *
   */
  public class Data{
    private Date date;
    private String weight;
    
    public Data(Date date, String weight) {
      this.date = date;
      this.weight = weight;
    }
    
    public Date getDate() {
      return this.date;
    }
    
    public String getWeight() {
      return this.weight;
    }
  }
  

