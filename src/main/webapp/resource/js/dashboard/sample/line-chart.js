/* 원본 : https://observablehq.com/@d3/line-chart
   참고 : https://falsy.me/d3-js-%eb%a5%bc-%ec%82%ac%ec%9a%a9%ed%95%98%ec%97%ac-%eb%8d%b0%ec%9d%b4%ed%84%b0-%ec%8b%9c%ea%b0%81%ed%99%94%ed%95%98%ea%b8%b0-5-area-charts-%ea%b7%b8%eb%9d%bc%eb%8d%b0%ec%9d%b4%ec%85%98/ */

const lineWidth1 = 500;
const lineHeight1 = 300;
const lineMargin1 = {top: 40, right: 40, bottom: 40, left: 40};
const linePadding1 = 30;
const lineData1 = [
  {date: new Date('2018-01-01'), value: 10},
  {date: new Date('2018-01-02'), value: 20},
  {date: new Date('2018-01-03'), value: 30},
  {date: new Date('2018-01-04'), value: 25},
  {date: new Date('2018-01-05'), value: 35},
  {date: new Date('2018-01-06'), value: 45},
  {date: new Date('2018-01-07'), value: 60},
  {date: new Date('2018-01-08'), value: 50}
];
 
const lineX1 = d3.scaleTime()
  .domain(d3.extent(lineData1, d => d.date))
  .range([lineMargin1.left + linePadding1, lineWidth1 - linePadding1]);
 
const lineY1 = d3.scaleLinear()
  .domain([0, d3.max(lineData1, d => d.value)]).nice()
  .range([lineHeight1 - lineMargin1.bottom, lineMargin1.top]);
 
const lineXAxis1 = g => g
  .attr("transform", `translate(0,${lineHeight1 - lineMargin1.bottom})`)
  .call(d3.axisBottom(lineX1).ticks(lineWidth1 / 90).tickSizeOuter(0))
  .call(g => g.select('.domain').remove())
  .call(g => g.selectAll('line').remove());
 
const lineYAxis1 = g => g
  .attr("transform", `translate(${lineMargin1.left},0)`)
  .call(d3.axisLeft(lineY1))
  .call(g => g.select('.domain').remove())
  .call(g => g.selectAll('line')
    .attr('x2', lineWidth1)
    .style('stroke', '#ddd'))
 
const line = d3.line()
  .defined(d => !isNaN(d.value))
  .x(d => lineX1(d.date))
  .y(d => lineY1(d.value));
 
const area = d3.area()
  .x(d => lineX1(d.date))
  .y0(lineY1(0))
  .y1(d => lineY1(d.value));
 
const lineSvg1 = d3.select('#line-svg-area1').append('svg').style('width', lineWidth1).style('height', lineHeight1);

lineSvg1.append('g').call(lineXAxis1);

lineSvg1.append('g').call(lineYAxis1);

const grad = lineSvg1.append("defs").append("linearGradient")
  .attr("id", "grad")
  .attr("x1", "0%")
  .attr("x2", "0%")
  .attr("y1", "0%")
  .attr("y2", "100%");

grad.append("stop")
  .attr("offset", "0%")
  .style("stop-color", "#7ab8aa")
  .style("stop-opacity", 1);

grad.append("stop")
  .attr("offset", "100%")
  .style("stop-color", "#7ab8aa")
   .style("stop-opacity", 0.4);

lineSvg1.append("path")
  .datum(lineData1)
	.style("fill", "url(#grad)")
  .attr("d", line)
  .attr("d", area);
  
lineSvg1.append("path")
  .datum(lineData1)
  .attr("fill", "none")
  .attr("stroke", "#add7a8")
  .attr("stroke-width", 6)
  .attr("stroke-linejoin", "round")
  .attr("stroke-linecap", "round")
  .attr("d", line);

lineSvg1.node();