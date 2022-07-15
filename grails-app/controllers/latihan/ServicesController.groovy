package latihan

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ServicesController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Services.list(params), model:[servicesCount: Services.count()]
    }

    def show(Services services) {
        respond services
    }

    def create() {
        respond new Services(params)
    }

    @Transactional
    def save(Services services) {
        if (services == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (services.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond services.errors, view:'create'
            return
        }

        services.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'services.label', default: 'Services'), services.id])
                redirect services
            }
            '*' { respond services, [status: CREATED] }
        }
    }

    def edit(Services services) {
        respond services
    }

    @Transactional
    def update(Services services) {
        if (services == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (services.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond services.errors, view:'edit'
            return
        }

        services.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'services.label', default: 'Services'), services.id])
                redirect services
            }
            '*'{ respond services, [status: OK] }
        }
    }

    @Transactional
    def delete(Services services) {

        if (services == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        services.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'services.label', default: 'Services'), services.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'services.label', default: 'Services'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
