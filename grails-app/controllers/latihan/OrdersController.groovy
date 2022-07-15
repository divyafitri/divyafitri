package latihan

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class OrdersController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Orders.list(params), model:[ordersCount: Orders.count()]
    }

    def show(Orders orders) {
        respond orders
    }

    def create() {
        respond new Orders(params)
    }

    @Transactional
    def save(Orders orders) {
        if (orders == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (orders.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orders.errors, view:'create'
            return
        }

        orders.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'orders.label', default: 'Orders'), orders.id])
                redirect orders
            }
            '*' { respond orders, [status: CREATED] }
        }
    }

    def edit(Orders orders) {
        respond orders
    }

    @Transactional
    def update(Orders orders) {
        if (orders == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (orders.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orders.errors, view:'edit'
            return
        }

        orders.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'orders.label', default: 'Orders'), orders.id])
                redirect orders
            }
            '*'{ respond orders, [status: OK] }
        }
    }

    @Transactional
    def delete(Orders orders) {

        if (orders == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        orders.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'orders.label', default: 'Orders'), orders.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'orders.label', default: 'Orders'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
