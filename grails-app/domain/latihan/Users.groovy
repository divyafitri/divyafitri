package latihan

import com.latihan.GlobalConfig
import groovyx.gpars.actor.impl.DDAClosure

class Users {
    String fullName
    String userName
    String phoneUser
    String password
    String userType = GlobalConfig.USER_TYPE.REGULAR_MEMBER
    String identityHash
    Date identityHashLastUpdate
    Boolean isActive = true

    Date dataCreated
    Date lastUpdated


    static constraints = {
        fullName nullable: false, blank:false
        userName unique: true,blank: false,nullable: false, minSize: 5
        phoneUser nullable: false, blank : false, matches: "[0-9]+"
        password nullable: false, blank: false
        identityHash nullable: true
        identityHashLastUpdate nullable: true
    }

    def beforeInsert(){
        this.password = this.password.encodeAsMD5()
    }
    def beforeUpdate(){
        this.password = this.password.encodeAsMD5()
    }
}
