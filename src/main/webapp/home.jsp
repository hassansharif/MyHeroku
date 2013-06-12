<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Salesforce Registration</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
<link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css" rel="stylesheet">
<script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>

<script src="http://code.jquery.com/jquery.js"></script>

</head>
<body>
	
	

	<form:form method="post" action="insert" commandName="sfcred" class="form-horizontal">
	<!-- <form action="/sfpoc/insert" class="form-horizontal">  -->
		<fieldset>

			<!-- Form Name -->
			<legend>Salesforce Details </legend>

			<!-- Text input-->
			<div class="control-group">
				<label class="control-label">Username</label>
				<div class="controls">
					<form:input path="username" type="text"
						placeholder="username@email" class="input-xlarge"/>

				</div>
			</div>

			<!-- Text input-->
			<div class="control-group">
				<label class="control-label">Password</label>
				<div class="controls">
					<form:input path="password" type="text"
						placeholder="password" class="input-xlarge"/>

				</div>
			</div>

			<!-- Text input-->
			<div class="control-group">
				<label class="control-label">Salesforce URL</label>
				<div class="controls">
					<form:input path="url" type="text"
						placeholder="Salesforce URL" class="input-xlarge"/>

				</div>
			</div>

			<!-- Text input-->
			<div class="control-group">
				<label class="control-label">Client ID</label>
				<div class="controls">
					<form:input path="clientId" type="text"
						placeholder="Client ID" class="input-xlarge"/>

				</div>
			</div>

			<!-- Text input-->
			<div class="control-group">
				<label class="control-label">Client Secret</label>
				<div class="controls">
					<form:input path="clientSecret" type="text"
						placeholder="Client Secret" class="input-xlarge"/>

				</div>
			</div>

			<!-- Button -->
			<div class="control-group">
				<label class="control-label"></label>
				<div class="controls">
					<button id="singlebutton" name="singlebutton"
						class="btn btn-success" type="submit">Generate Key</button>
				</div>
			</div>
			
			
			<c:if  test="${!empty sfcred.key}">
                <h3>Generated Key</h3>
                <table class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>Key</th>
                        <th>&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    	<tr>
                            <td>${sfcred.key}</td>
                        </tr>
                    </tbody>
                </table>
            </c:if>

		</fieldset>
	</form:form>

</body>
</html>