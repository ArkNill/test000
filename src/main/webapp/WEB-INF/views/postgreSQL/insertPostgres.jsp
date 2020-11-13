<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Insert Into Postgres</title>
	
    <!-- Custom fonts for this template-->
    <link href="${pageContext.request.contextPath}/resource/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="${pageContext.request.contextPath}/resource/css/sb-admin-2.min.css" rel="stylesheet">
	
	<!-- w3 css -->
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	
	<!-- 로딩 이미지 -->
	<style>
	.loading{
	  position:absolute;
	  top:50%;
	  left:50%;
	  width:80px;
	  height:80px;
	  margin:-50px 0 0 -50px;
	}
	</style>
</head>

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- 업로드시 로딩 화면 -->
    <div id="loadingArea" class="w3-modal w3-animate-opacity">
		<img class="loading" width="100px" src="${pageContext.request.contextPath}/resource/img/loading.gif">
	</div>

    <!-- 사이드바 include-->
    <%@ include file="../include/sidebar.jsp"%>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <!-- 이 부분만 바꿔주면 됩니다 -->
        <div id="content">
            <!-- 툴바 include -->
            <%@ include file="../include/toolbar.jsp"%>
            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-2 text-gray-800">데이터 입력</h1>
                </div>

                <!-- Page Body -->
                <div>
                    <!-- Json 파일 업로드 -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">한국어 강의 Json 파일 업로드</h6>
                        </div>
                        <div class="card-body">
                            <form id="jsonUpload">
                                <input type="file" id="jsonFile" name="jsonFile" accept="*" multiple>
                                <hr>
                                <button type="button" onclick="jsonUpload();" class="btn btn-primary btn-icon-split">
                                        <span class="icon text-white-50">
                                            <i class="fas fa-check"></i>
                                        </span>
                                    <span class="text">업로드</span>
                                </button>
                            </form>
<%--                            <a href="${pageContext.request.contextPath}/insertJSONIntoPostgre">json 파일 업로드</a>--%>
                        </div>
                    </div>

                    <!-- Excel 파일 업로드 -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">강의 프로그램 Excel 파일 업로드</h6>
                        </div>
                        <div class="card-body">
                            <form id="xlsxUpload">
                                <input type="file" id="xlsxFile" name="xlsxFile" accept="*" multiple>
                                <hr>
                                <button type="button" onclick="xlsxUpload();" class="btn btn-primary btn-icon-split">
                                        <span class="icon text-white-50">
                                            <i class="fas fa-check"></i>
                                        </span>
                                    <span class="text">업로드</span>
                                </button>
                            </form>
<%--                            <a href="${pageContext.request.contextPath}/insertXlsxIntoPostgre">Excel 파일 업로드</a>--%>
                        </div>
                    </div>
                </div>

            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- End of Main Content -->

        <!-- footer include-->
        <%@ include file="../include/footer.jsp"%>

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>

<!-- 파일 업로드 함수 -->
<script>
    // json 파일 업로드
    function jsonUpload() {
        var res = '';

        var formData = new FormData($('#jsonUpload')[0]);

        // 파일 형식 확인
        for(var item of formData.entries()) {
            var name = item[1]["name"];
            if(name.substring(name.length-5, name.length) != '.json'){
                alert("json 파일을 업로드해주세요.");
                return;
            }

        }

        $.ajax({
            type:"POST",
            enctype: 'multipart/form-data',
            url: "${pageContext.request.contextPath}/jsonUpload",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,

            success:function (result){
                res = result;
                if(res == 'true'){
                    alert("파일을 성공적으로 업로드했습니다.")
                    document.getElementById('loadingArea').style.display='none';
                }
                else if(res == 'false'){
                    alert("이미 업로드한 파일입니다.")
                    document.getElementById('loadingArea').style.display='none';
                }
            },
            error: function (){
                alert("업로드한 파일 용량이 너무 큽니다.");
                document.getElementById('loadingArea').style.display='none';
            }
        });
        document.getElementById('loadingArea').style.display='block';
    }

    // xlsx 파일 업로드
    function xlsxUpload() {
        var res = '';

        var formData = new FormData($('#xlsxUpload')[0]);

        // 파일 형식 확인
        for(var item of formData.entries()) {
            var name = item[1]["name"];
            if(name.substring(name.length-5, name.length) != '.xlsx'){
                alert("엑셀 파일을 업로드해주세요.");
                return;
            }

        }

        $.ajax({
            type:"POST",
            enctype: 'multipart/form-data',
            url: "${pageContext.request.contextPath}/xlsxUpload",
            data: formData,
            processData: false,
            contentType: false,
            cache: false,

            success:function (result){
                res = result;
                if(res == 'true'){
	                	alert("파일을 성공적으로 업로드했습니다.")
	                	document.getElementById('loadingArea').style.display='none';
	                }
                else if(res == 'false'){
                		alert("이미 업로드한 파일입니다.")
                		document.getElementById('loadingArea').style.display='none';
	                }
            },
            error: function (){
                alert("업로드한 파일 용량이 너무 큽니다.");
                document.getElementById('loadingArea').style.display='none';
            }
        });
        document.getElementById('loadingArea').style.display='block';
    }
</script>

<!-- Bootstrap core JavaScript-->
<script src="${pageContext.request.contextPath}/resource/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resource/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="${pageContext.request.contextPath}/resource/vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="${pageContext.request.contextPath}/resource/js/sb-admin-2.min.js"></script>

<!-- Page level plugins -->
<script src="${pageContext.request.contextPath}/resource/vendor/chart.js/Chart.min.js"></script>

<!-- Page level custom scripts -->
<script src="${pageContext.request.contextPath}/resource/js/demo/chart-area-demo.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/demo/chart-pie-demo.js"></script>
</body>

</html>