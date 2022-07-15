package latihan

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MerchantsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Merchants.list(params), model:[merchantsCount: Merchants.count()]
    }

    def show(Merchants merchants) {
        respond merchants
    }

    def create() {
        respond new Merchants(params)
    }

    @Transactional
    def save(Merchants merchants) {
        if (merchants == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (merchants.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond merchants.errors, view:'create'
            return
        }

        merchants.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'merchants.label', default: 'Merchants'), merchants.id])
                redirect merchants
            }
            '*' { respond merchants, [status: CREATED] }
        }
    }

    def edit(Merchants merchants) {
        respond merchants
    }

    @Transactional
    def update(Merchants merchants) {
        if (merchants == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (merchants.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond merchants.errors, view:'edit'
            return
        }

        merchants.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'merchants.label', default: 'Merchants'), merchants.id])
                redirect merchants
            }
            '*'{ respond merchants, [status: OK] }
        }
    }

    @Transactional
    def delete(Merchants merchants) {

        if (merchants == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        merchants.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'merchants.label', default: 'Merchants'), merchants.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'merchants.label', default: 'Merchants'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
