package dev.edmt.investoraplikasi;

/**
 * Created by Protek on 03/03/2018.
 */

public class m_verifikasi {
   String name;

   public m_verifikasi(String name, String email, String filePath){
       this.name = name;
       this.email = email;
       this.filePath = filePath;
   }


   String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    String filePath;


   public m_verifikasi(){

   }
}
