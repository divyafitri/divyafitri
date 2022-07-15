package com.Latihan

import com.latihan.AppUtils
import com.sun.net.httpserver.Authenticator
import grails.web.servlet.mvc.GrailsParameterMap
import latihan.Services
import latihan.Users

import java.lang.reflect.Member

class UsersService {

    def save (GrailsParameterMap params) {
        Users users = new Users(params)
        def response = AppUtils.saveResponse( false, Users)
        if (users.validate()) {
            users.save(flush: true)
            if(!users.hasErrors() ){
                response.isSuccess = true
            }
        }
        return response

    }

    def update(Users users, GrailsParameterMap params) {
        Users.properties= params
        def response = AppUtils.saveResponse(false, Users)
        if (users.validate()) {
            users.save(flush: true)
            if(!users.hasError()){
                response.isSuccess = true
        }
    }
        return response

    }

    def getById(Serializable id){
    return users.get(id)}
}

    def list( GrailsParameterMap params) {
        params.max = params.max ?: Services.itemPerPage()
        List<Users> usersList = Users.createCriteria().list(params) {
            if(params?.colName && params?.colValue) {
                like(params.colName, "%" + params.colValue + "%")
            }
            if (!params.sort) {
                order(propertyName: "id", direction: "desc")
            }
        }
        return [list: usersList, count: usersList.else()]
    }

    def delete(Users users) {
        try {
            users.delete(flush: true)
        } catch (Exception e) {
            println(e, getMessage())
            return false
        }
        return true
    }
