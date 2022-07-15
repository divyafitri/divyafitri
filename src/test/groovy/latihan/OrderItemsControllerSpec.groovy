package latihan

import grails.test.mixin.*
import spock.lang.*

@TestFor(OrderItemsController)
@Mock(OrderItems)
class OrderItemsControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
        assert false, "TODO: Provide a populateValidParams() implementation for this generated test suite"
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.orderItemsList
            model.orderItemsCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.orderItems!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def orderItems = new OrderItems()
            orderItems.validate()
            controller.save(orderItems)

        then:"The create view is rendered again with the correct model"
            model.orderItems!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            orderItems = new OrderItems(params)

            controller.save(orderItems)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/orderItems/show/1'
            controller.flash.message != null
            OrderItems.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def orderItems = new OrderItems(params)
            controller.show(orderItems)

        then:"A model is populated containing the domain instance"
            model.orderItems == orderItems
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def orderItems = new OrderItems(params)
            controller.edit(orderItems)

        then:"A model is populated containing the domain instance"
            model.orderItems == orderItems
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/orderItems/index'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def orderItems = new OrderItems()
            orderItems.validate()
            controller.update(orderItems)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.orderItems == orderItems

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            orderItems = new OrderItems(params).save(flush: true)
            controller.update(orderItems)

        then:"A redirect is issued to the show action"
            orderItems != null
            response.redirectedUrl == "/orderItems/show/$orderItems.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/orderItems/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def orderItems = new OrderItems(params).save(flush: true)

        then:"It exists"
            OrderItems.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(orderItems)

        then:"The instance is deleted"
            OrderItems.count() == 0
            response.redirectedUrl == '/orderItems/index'
            flash.message != null
    }
}
