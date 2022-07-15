package latihan

class Orders {
    Integer userId
    Integer status
    Date finishedAt
    Double totalOrder
    String customer

    static constraints = {
        userId nullable: false, unique: false
        status nullable: false, blank: false
        finishedAt nullable: false,blank: false
        totalOrder nullable: false,blank: false
        customer nullable: false,blank: false

    }
}
