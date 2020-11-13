/* 원본 : https://beta.observablehq.com/@mbostock/d3-line-chart 
   참고 : https://falsy.me/d3-js-%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%8B%9C%EA%B0%81%ED%99%94%ED%95%98%EA%B8%B0-1-line-charts/ */
   
const width = 400;
const height = 400;
const margin = {top: 40, left: 40, bottom: 40, right: 40};
 
const data = [
    {name: 'a', value: 10},
    {name: 'b', value: 29},
    {name: 'c', value: 32},
    {name: 'd', value: 25},
    {name: 'e', value: 23},
    {name: 'f', value: 15}
  ];
 
const x = d3.scaleBand()
  .domain(data.map(d => d.name))
  .range([margin.left, width - margin.right])
  .padding(0.2);
 
const y = d3.scaleLinear()
  .domain([0, d3.max(data, d => d.value)]).nice()
    .range([height - margin.bottom, margin.top]);
 
const xAxis = g => g
  .attr('transform', `translate(0, ${height - margin.bottom})`)
  .call(d3.axisBottom(x)
    .tickSizeOuter(0));
 
const yAxis = g => g
  .attr('transform', `translate(${margin.left}, 0)`)
  .call(d3.axisLeft(y))
  .call(g => g.select('.domain').remove());
 
const svg = d3.select('body').append('svg').style('width', width).style('height', height);
 
svg.append('g').call(xAxis);
svg.append('g').call(yAxis);
svg.append('g')
  .attr('fill', 'steelblue')
  .selectAll('rect').data(data).enter().append('rect')
  .attr('x', d => x(d.name))
  .attr('y', d => y(d.value))
  .attr('height', d => y(0) - y(d.value))
  .attr('width', x.bandwidth());

svg.node();