<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        <g:layoutTitle default="Laundry-Cash"/>
    </title>

    <asset:stylesheet src="application.css"/>
    <g:layoutHead/>
</head>

<body>

 <header>
     <nav class="navbar navbar-expand-lg navbar-dark fixed-top bg-dark rounded" style="background-color: #0b2e13">
         <a class="navbar-brand" href="#">Laundry-Cash</a>
         <button class="navbar-toggler d-lg-none" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
             <span class="navbar-toggler-icon"></span>
         </button>
 </header>
<div class="container-fluid">
    <div class="row">

        <nav class="col-sm-3 col-md-2 d-none d-sm-block bg-light sidebar">
            <ul class="list-group">
                <li class="list-group-item"><a href="#">Dashboard</a></li>
                <li class="list-group-item"><a href="#">Orders</a></li>
                <li class="list-group-item"><a href="#">List Orders</a></li>
            </ul>
        </nav>
        <main role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
            <g:layoutBody/>
        </main>
    </div>

</div>


</body>
</html>
