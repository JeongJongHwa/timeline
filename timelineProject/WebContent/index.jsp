<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="java.io.*,java.net.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>



<script type="text/javascript" >
$(function() {
	
	$("#deleteBtn").on('click',function(e){
		
		e.preventDefault();
		
		var result = confirm('정말로 삭제하시겠습니까?');
		
		if( result ){
			
			
			var param = {
					tid:$("#deleteTid").val()
			};
			
			$.ajax({
				
				type:'post',
				url:"TimeDelete",
				contentType: "application/json; charset=utf-8",
				data:JSON.stringify(param),
				dataType:'text',
			
				success:function(data){
						
					$("#btn").trigger('click');
				},
				error:function(request, status, error){
					 var msg = "ERROR : " + request.status + "<br>"
				      msg +=  + "내용 : " + request.responseText + "<br>" + error;
					 console.log("실패 : " + msg );
				}
				
			});
			
		} else {
			console.log('no');	
		}
		
		
		
		
	});
	
	$("#updateBtn").on('click',function(e){
		
		e.preventDefault();
		
		var param = {
				tid:$("#updateTid").val(),
				comment:$("#updateComment").val()
		};
		
		$.ajax({
			
			type:'post',
			url:"TimeUpdate",
			contentType: "application/json; charset=utf-8",
			data:JSON.stringify(param),
			dataType:'text',
		
			success:function(data){
					
				$("#btn").trigger('click');
			},
			error:function(request, status, error){
				 var msg = "ERROR : " + request.status + "<br>"
			      msg +=  + "내용 : " + request.responseText + "<br>" + error;
				 console.log("실패 : " + msg );
			}
			
		});
		
	});
	
	$("#insertBtn").on('click',function(e){
		
		e.preventDefault();
		
		var param = {
				comment:$("#comment").val()
		};
			
		$.ajax({
			
			type:'post',
			url:"TimeInsert",
			contentType: "application/json; charset=utf-8",
			data:JSON.stringify(param),
			dataType:'text',
		
			success:function(data){
					
				$("#btn").trigger('click');
			},
			error:function(request, status, error){
				 var msg = "ERROR : " + request.status + "<br>"
			      msg +=  + "내용 : " + request.responseText + "<br>" + error;
				 console.log("실패 : " + msg );
			}
			
		});
		
	});
	
	$("#btn").on('click',function(e){
		
		e.preventDefault();
		
	var param = {
			URL:"https://barosa.me:6611/timeline"
	}
		
	$.ajax({
			type:'post',
			url:"TimeList",
			contentType: "application/json; charset=utf-8",
			data:JSON.stringify(param),
			dataType:'text',
			success:function(data){
				
				$("#listTable").html("");
				var tableHead = "<thead><tr><th class='text-center'>tid</th><th class='text-center'>comment</th><th class='text-center'>Time</th></tr></thead>";
				
				var result = JSON.parse ( data );
				//var result = JSON.parse ( data.substr( data.indexOf("[") ));
				
				var str = tableHead;
				
				$.each( result, function(i){
					
					str += "<tr><td>"+result[i].tid+"</td><td>"+result[i].comment+"</td><td>"+
						result[i].regdate+"</td></tr>";
					
				});
				
				$("#listTable").append( str );
				
				
				
			},
			error:function(request, status, error){
				 var msg = "ERROR : " + request.status + "<br>"
			      msg +=  + "내용 : " + request.responseText + "<br>" + error;
				 console.log("실패 : " + msg );
			}
		    
		});
		
	});
	
	$("#btn").trigger('click');
	
});

</script>
<title>Time board</title>
</head>

<body>
	<div class="navbar navbar-inverse">
		<div class="container">
			<ul class="nav navbar-nav">
				<li class="active"><a class="navbar-brand" id="btn">Time</a></li>
				<li><a >time List</a></li>
			</ul>
		</div>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="col-sm-8">
			<table id="listTable" class="table text-center table-bordered">
				<thead>
					<tr>
						<th class="text-center">tid</th>
						<th class="text-center">comment</th>
						<th class="text-center">Time</th>
					</tr>
				</thead>
				<tbody><tr><td></td><td></td><td></td></tr></tbody>
			</table>
			</div>
			<p>추가하고싶은 Comment를 입력해주세요</p>
			<div class="col-sm-4"><input type="text" id="comment" name="comment" class="form-data" />
			<button type="button" id="insertBtn" class="btn btn-primary" >추가하기</button><br/>
			</div>
			
			<p>수정하고싶은 tid와 Comment를 입력해주세요</p>
			<label for="updateTid">tid : </label>
			<input type="text" id="updateTid" name="updateTid" class="form-data" /><br/>
			<label for="updateComment">comment : </label>
			<input type="text" id="updateComment" name="updateComment" class="form-data" />
			<button type="button" id="updateBtn" class="btn btn-primary" >수정하기</button><br/>
			
			<p>삭제하고싶은 tid를 입력해주세요</p>
			<input type="text" id="deleteTid" name="deleteTid" class="form-data" />
			<button type="button" id="deleteBtn" class="btn btn-primary" >삭제하기</button>
			
			
		</div>
	</div>


</body>
</html>