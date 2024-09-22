package DTO;

public class TaiKhoan {
    public String name;
    public String email;
    public String password;
    public String role;

        public TaiKhoan() {

        }

        public TaiKhoan(String name, String email, String password, String role) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.role = role;
        }
}
