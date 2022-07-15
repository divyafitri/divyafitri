package latihan

class Services {
    String name
    Double price
    Integer time

    static constraints = {
        name nullable: false, unique: false
        price nullable: false, blank: false
        time nullable: false,blank: false
    }
}