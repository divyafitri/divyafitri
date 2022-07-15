package com.latihan


import latihan.Users
import org.grails.web.util.WebUtils

class AppUtils {

        static saveResponse (Boolean isSuccess, def model) {
            return [isSuccess: isSuccess, model: model]
        }

        static getAppSession(){

        }

        static infoMessage (String message, boolean status = true) {
            return{info message, success: status}
        }
}
