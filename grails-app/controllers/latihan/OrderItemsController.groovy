package latihan

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class OrderItemsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond OrderItems.list(params), model:[orderItemsCount: OrderItems.count()]
    }

    def show(OrderItems orderItems) {
        respond orderItems
    }

    def create() {
        respond new OrderItems(params)
    }

    @Transactional
    def save(OrderItems orderItems) {
        if (orderItems == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (orderItems.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orderItems.errors, view:'create'
            return
        }

        orderItems.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'orderItems.label', default: 'OrderItems'), orderItems.id])
                redirect orderItems
            }
            '*' { respond orderItems, [status: CREATED] }
        }
    }

    def edit(OrderItems orderItems) {
        respond orderItems
    }

    @Transactional
    def update(OrderItems orderItems) {
        if (orderItems == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (orderItems.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orderItems.errors, view:'edit'
            return
        }

        orderItems.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'orderItems.label', default: 'OrderItems'), orderItems.id])
                redirect orderItems
            }
            '*'{ respond orderItems, [status: OK] }
        }
    }

    @Transactional
    def delete(OrderItems orderItems) {

        if (orderItems == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        orderItems.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'orderItems.label', default: 'OrderItems'), orderItems.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'orderItems.label', default: 'OrderItems'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
