<%--
  Created by IntelliJ IDEA.
  User: marti
  Date: 7/27/2018
  Time: 3:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@include file="/WEB-INF/views/template/header.jsp"%>
<script>
    //once loaded...
    $(document).ready(function(){

        //select table class inside document, to apply dataTables functions
        var table= $('.table').DataTable({
            "searching": false,
            "info": false
        });

    });
</script>

<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Customer Management Page</h1>

            <p class="lead">This is the customer management page</p>
        </div>
        <div class="container" style="margin-bottom: 10px">
            <div class="col-md-30">
                <a href="<c:url value="/admin"/>" class="btn btn-default pull-left" >Back to Admin Page</a>
            </div>
        </div>
        <div class="container">
            <table class="table table-striped table-hover">
                <thead>
                <tr class="bg-success">

                    <th>Username</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th style="width: 20%">Email</th>
                    <th>Phone number</th>
                    <th>Enabled</th>
                    <th style="width: 5%"></th>


                </tr>
                </thead>
                <c:forEach items="${customerList}" var="customer">
                    <tr>
                        <td>${customer.username}</td>
                        <td>${customer.customerName}</td>
                        <td>${customer.customerSurname}</td>
                        <td>${customer.customerEmail}</td>
                        <td>${customer.customerPhone}</td>
                        <td>${customer.users.enabled}</td>
                        <td><a href="<spring:url value="/admin/users/viewCustomer/${customer.customerId}" />"
                        ><span class="glyphicon glyphicon-info-sign"></span></a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <%@include file="/WEB-INF/views/template/footer.jsp" %>
