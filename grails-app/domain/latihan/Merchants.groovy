package latihan

class Merchants {
    String merchantCode
    String merchantName


    static constraints = {
        merchantCode nullable: false, unique: true
        merchantName nullable: false, blank: false

    }
}
