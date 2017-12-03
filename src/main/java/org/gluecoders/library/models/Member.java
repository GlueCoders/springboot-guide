package org.gluecoders.library.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Anand_Rajneesh on 6/10/2017.
 */
@Entity
public class Member {

    @Id
    @GeneratedValue
    private String id;
    private String firstName;
    private String lastName;
    private Long mobile;
    private String email;
    private LocalDate dob;
    //private Address address;
    //private List<IssuedBook> issuedBooks;

    public Member() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /*public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
*/
    /*public List<IssuedBook> getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(List<IssuedBook> issuedBooks) {
        this.issuedBooks = issuedBooks;
    }
*/
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static class Address {
        private String city;
        private String state;
        private String postCode;
        private String houseNo;
        private String street;

        public Address() {
        }

        public Address(String city, String state, String postCode, String houseNo, String street) {
            this.city = city;
            this.state = state;
            this.postCode = postCode;
            this.houseNo = houseNo;
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }
    }

    public static class IssuedBook{
        private String isbn;
        private LocalDate issuedOn;
        private LocalDate expiryOn;

        public IssuedBook() {
        }

        public IssuedBook(String isbn, int expiryDays) {
            this.isbn = isbn;
            LocalDate now = LocalDate.now();
            issuedOn = now;
            expiryOn = now.plusDays(expiryDays);
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public LocalDate getIssuedOn() {
            return issuedOn;
        }

        public void setIssuedOn(LocalDate issuedOn) {
            this.issuedOn = issuedOn;
        }

        public LocalDate getExpiryOn() {
            return expiryOn;
        }

        public void setExpiryOn(LocalDate expiryOn) {
            this.expiryOn = expiryOn;
        }
    }
}
