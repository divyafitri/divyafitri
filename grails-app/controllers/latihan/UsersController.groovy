package latihan

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Transactional(readOnly = true)
class UsersController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Users.list(params), model:[usersCount: Users.count()]
    }

    def show(Users users) {
        respond users
    }

    def create() {
        respond new Users(params)
    }

    @Transactional
    def save(Users users) {
        if (users == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (users.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond users.errors, view:'create'
            return
        }
        users.password= users.password.hashCode().toString()
        users.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'users.label', default: 'Users'), users.id])
                redirect users
            }
            '*' { respond users, [status: CREATED] }
        }
    }

    def edit(Users users) {
        respond users
    }

    @Transactional
    def update(Users users) {
        if (users == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (users.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond users.errors, view:'edit'
            return
        }

        users.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'users.label', default: 'Users'), users.id])
                redirect users
            }
            '*'{ respond users, [status: OK] }
        }
    }

    @Transactional
    def delete(Users users) {

        if (users == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        users.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'users.label', default: 'Users'), users.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'users.label', default: 'Users'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
