<?php

/*	Created by: Travis Spuller
 * 	Last Updated: 10/21/2016
 * 	Wepage: maghub/ticketcenter/metrics/support
 * 	Description: User can search between a start and end date of support tickets.  They may also select support types
 * 				for data in a bar graph. Once form is submitted, data from /ReportsUtil.php/ create two bar graph. 
 * 				1)The percent of tickets answered how fast (hours)
 * 				2)How many comments it took to complete a ticket
 */

 //Load associated script for Highcharts
Javascript::loadAll(array(
    '/system/js/highcharts/highcharts.js',
    '/system/js/highcharts/no-data-to-display.js',
));
//get file name of submitted form
$rid = Report::findIdByFileName(basename(__FILE__));

$paginator = new Paginator('data_table');
$paginator->set_number_per_page_default(50)
->set_page_options(50,100,250,-1)->enable_sticky_header();

?>

<div class="sixteen columns">
	<h1>Support Ticket Time</h1>
</div>

<?php //start search box ?>
<div class="container-content container-no-bottom row">
	<?php //send form in show() to process in ReportsUtil.php ?>
	<form method="GET" name="dateform" onsubmit="$('#search-loading').show();" >
		<input type='hidden' name='rid' value='<?php echo Get::read('rid'); ?>' />
		
		<div class="row half-bottom container-margin9">
			<?php //Date Range Input ?>
			<div class="five columns">
				<label>Reporting Period</label>
				<?php //Beginning Date Range ?>
				From: <input type='text' name='start_date' id='start_date'
					value='<?php if(Get::read('start_date')): ?><?php echo Get::read('start_date'); ?><?php endif; ?>' class='datepicker width-120'>
    			<?php //Ending Date Range ?>
				To: <input type='text' name='end_date' id='end_date'
					value='<?php if(Get::read('end_date')): ?><?php echo Get::read('end_date'); ?><?php endif; ?>' class='datepicker width-120'>
			</div>
			<?php //Support Type Selector ?>
			<div class="seven columns">
				<label>Support Type</label>
				<?php//Support type will be an array because of multiple inputs ?>
				<select name='support_type[]' multiple>
				<?php
					//Query for Support Types eg. Maghub - Support
					$results = Mysql::query("SELECT sID, CONCAT(service_categories.scat_name,' - ',services.service_name) AS service_name
						FROM services
						INNER JOIN service_categories ON service_categories.scatID = services.scatID
						WHERE is_support = 1 ORDER BY service_name"); ?>
					<?php //Iterate through results to display each support type ?>
					<?php while ( $row = Mysql::fetchAssoc($results) ): ?>
						<option value="<?php echo $row['sID']?>" <?php echo (Get::read('support_type') && in_array($row['sID'], Get::read('support_type')) ? 'selected' : '')?>><?php echo $row['service_name'] ?></option>
						
					<?php endwhile; ?>
				
				</select>
			</div>
		</div>
		<?php //area for loading after submition ?>
		<div class="container-box-bottom">
			<?php //only show when show is 1 (when submitted form) ?>
			<input type='hidden' name='show' value='1' />
			<div id="search-loading" style="display:none;">
				<div class='center'>Please Wait ... <img src="/images/ajax-loader2.gif"></div>
			</div>
			<input type="submit" value="GET REPORT" />
			<?php //clear search form ?>
			<a href="/ticketcenter/metrics/kpi/?rid=?rid=<?php echo $rid;?>" class="button_cancel">Reset</a>
		</div>
	</form>
</div> 

<?php //area for graphs / only show if form has been submitted?>
<?php if (Get::read('show') == 1): ?>
<div class="row half-bottom max-width">
	<div class="eight columns">
		<div class="margin-top-40 container-content container-no-bottom">
			<div id="graph1" style="display: block;">
				
			</div>
		</div>
	</div>
	<div class="eight columns">
		<div class="margin-top-40 container-content container-no-bottom">
			<div id="graph2" style="display: block;">
				
			</div>
		</div>
	</div>
</div>
<?php //Space between graph and table ?>
<div class='margin-top-60'></div>
<?php endif; ?>
<?php //end all html ?>

<?php
//function when sumbit is clicked
if ( Get::read('show') ) {
	
	$start_filter = Get::read('start_date');
	$end_filter = Get::read('end_date');

	$type_filter = Get::read('support_type');
	
	$datas = array();
	$params = array();

	if ( Get::read('start_date') ) $params['start_date'] = date("Y-m-d",strtotime(Get::read('start_date')));
	if ( Get::read('end_date') ) $params['end_date'] = date("Y-m-d",strtotime(Get::read('end_date')));
	
	// Get Report data
	list($data, $timeResponseData, $commentData) = ReportsUtil::ticketMetricsByTime($params, $start_filter, $end_filter, $type_filter);
	//value is the total number of tickets used in data
	$value = count($data);
	}
?>

<script>
	//create both graphs
	$(function () {
   	//graph 1: Tickets by First Commented Time
    <?php if(Get::read('show') == 1): ?> //Show when submitted
    $('#graph1').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Support Ticket Response Time (<?php echo $value?> tickets)'
        },
        xAxis: {
        	type: 'category'
           	
        },
        yAxis: {
            title: {
                text: 'Ticket Percentage'
            }

        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:.1f}%'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
        },

        series: [{
            name: 'Hours',
            colorByPoint: true,
            data: [
          	<?php if($value != 0): ?>
            <?php 
            foreach($timeResponseData AS $label=>$chart): ?>
            {
                name: '<?php echo $label; ?>',
                y: <?php echo (round($chart/$value, 2) * 100); ?>,
                drilldown: '<?php echo $label; ?>'
            },
            <?php 
            endforeach; ?>
            <?php endif; ?>
            ]
        }],
    });
    //graph 2: Tickets by Number of Comments needed to answer
    $('#graph2').highcharts({
    	chart: {
            type: 'column'
        },
        title: {
            text: 'Support Ticket Comments (<?php echo $value?> tickets)'
        },
        xAxis: {
        	type: 'category'
           	
        },
        yAxis: {
            title: {
                text: 'Ticket Percentage'
            }

        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:.1f}%'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
        },
		
        series: [{
            name: 'Comments',
            colorByPoint: true,
            data: [
            	<?php if($value != 0): ?>
            	<?php foreach($commentData AS $label=>$chart): ?>
            	{
                name: '<?php echo $label; ?>',
                y: <?php echo (round($chart/$value, 2) * 100); ?>,
                drilldown: '<?php echo $label; ?>'
           		},
           		<?php endforeach; ?>
           		<?php endif; ?>
           	]
        }],
    });
    <?php endif; ?>
});

</script>

<?php
// Present Table Results
	if(Get::read('show') == 1):
	//load in paginator
	$paginator->pagerTop();
	//table head
	?>
	<table class="max-width" id="data_table">
		<thead>
			<th>Ticket Name</th>
			<th>Created</th>
			<th>Response Time (hours)</th>
			<th>Total Comments</th>
			<th width="5%">View</th>
		</thead>
	<?php //always project head, even if there is no data ?>
	
	
	<?php//cycle through results?>		
	<tbody>
	<?php if ( count($data) > 0 ): ?>
		<?php foreach ( $data as $key => $row): ?>
			<?php $date = new DateTime($row['created']); ?>
			<tr>
				<td><?php echo $row['name'] ?></td>
				<td><?php echo $date->format('d/m/Y')?></td>
				<td><?php echo round($row['difference'], 2) ?></td>
				<td><?php echo $row['total_comments'] ?></td>
				<td><a title="View Ticket" href="/ticketcenter/jobtickets/jobjacket/?id=<?php echo $row['id']?>" target="blank"><?php echo $row['id'] ?></a></td>
			</tr>
		
		<?php endforeach; ?>
	<?php else: ?>
		<?php//if no results, display this?>
		<tr><td colspan="7">No results found</td></tr>
	<?php endif; ?>
		</tbody>
	</table>
	
	<?php $paginator->pagerBottom();
	endif; ?>
