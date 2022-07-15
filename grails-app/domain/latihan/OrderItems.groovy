package latihan

class  OrderItems {
    Integer orderId
    Integer servicesId
    Integer quantity

    static constraints = {
        orderId nullable : false
        servicesId nullable: false, unique: false
        quantity nullable: false
    }
}
