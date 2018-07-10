<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript" src="${ctx}/webjars/chartjs/1.0.1/Chart.min.js"></script>
<script type="text/javascript" src="${ctx}/javascript/example/bootstrap/lineChart.js"></script>
<div class="row">
	<div class="col-md-8">
		<p class="text-center">
			<strong>折线图</strong>
		</p>
		<div class="chart">
			<!-- Sales Chart Canvas -->
			<canvas id="lineChart" height="200px"></canvas>
		</div>
		<!-- /.chart-responsive -->
	</div>
</div>