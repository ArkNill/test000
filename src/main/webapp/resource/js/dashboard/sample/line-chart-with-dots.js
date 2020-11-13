/* 원본 : https://observablehq.com/@d3/line-chart
   참고 : https://falsy.me/d3-js-%eb%a5%bc-%ec%82%ac%ec%9a%a9%ed%95%98%ec%97%ac-%eb%8d%b0%ec%9d%b4%ed%84%b0-%ec%8b%9c%ea%b0%81%ed%99%94%ed%95%98%ea%b8%b0-5-area-charts-%ea%b7%b8%eb%9d%bc%eb%8d%b0%ec%9d%b4%ec%85%98/ */

const width = 500;
const height = 300;
const margin = {top: 40, right: 40, bottom: 40, left: 40};
const padding = 30;
const data = [
  {date: new Date('2018-01-01'), value: 10},
  {date: new Date('2018-01-02'), value: 20},
  {date: new Date('2018-01-03'), value: 30},
  {date: new Date('2018-01-04'), value: 25},
  {date: new Date('2018-01-05'), value: 35},
  {date: new Date('2018-01-06'), value: 45},
  {date: new Date('2018-01-07'), value: 60},
  {date: new Date('2018-01-08'), value: 50}
];
 
const x = d3.scaleTime()
  .domain(d3.extent(data, d => d.date))
  .range([margin.left + padding, width - padding]);
 
const y = d3.scaleLinear()
  .domain([0, d3.max(data, d => d.value)]).nice()
  .range([height - margin.bottom, margin.top]);
 
const xAxis = g => g
  .attr("transform", `translate(0,${height - margin.bottom})`)
  .call(d3.axisBottom(x).ticks(width / 90).tickSizeOuter(0))
  .call(g => g.select('.domain').remove())
  .call(g => g.selectAll('line').remove());
 
const yAxis = g => g
  .attr("transform", `translate(${margin.left},0)`)
  .call(d3.axisLeft(y))
  .call(g => g.select('.domain').remove())
  .call(g => g.selectAll('line')
    .attr('x2', width)
    .style('stroke', '#ddd'))
 
const line = d3.line()
  .defined(d => !isNaN(d.value))
  .x(d => x(d.date))
  .y(d => y(d.value));
 
const area = d3.area()
  .x(d => x(d.date))
  .y0(y(0))
  .y1(d => y(d.value));
 
const svg = d3.select('body').append('svg').style('width', width).style('height', height);

svg.append('g').call(xAxis);

svg.append('g').call(yAxis);

const grad = svg.append("defs").append("linearGradient")
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

svg.append("path")
  .datum(data)
	.style("fill", "url(#grad)")
  .attr("d", line)
  .attr("d", area);
  
svg.append("path")
  .datum(data)
  .attr("fill", "none")
  .attr("stroke", "#add7a8")
  .attr("stroke-width", 6)
  .attr("stroke-linejoin", "round")
  .attr("stroke-linecap", "round")
  .attr("d", line);
  
svg.append("g")
  .selectAll("dot")
  .data(data)
  .enter()
  .append("circle")
    .attr("cx", d => x(d.date))
    .attr("cy", d => y(d.value))
    .attr("r", 5)
    .attr("stroke", '#5487b1')
    .attr("stroke-width", 4)
    .attr("fill", "white")

svg.node();