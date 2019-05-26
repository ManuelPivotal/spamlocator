select sl.email_subject, 
	jrh.received_header_index, 
	hhf.host_hop_name as from_name,
	-- hhf.id,
	hhf.host_hop_ip as from_ip, 
	hhb.host_hop_name as by_name, 
	igl.continent_code,
	igl.country_name
  from spam_locator sl
  join spam_locator_received_header jrh on (sl.id = jrh.spam_locator_id)
  join received_header rh on (jrh.received_header = rh.id)
  left join host_hop hhf on (hhf.id = rh.from_id)
  left join host_hop hhb on (hhb.id = rh.by_id)
  left join ip_geo_location igl on (igl.ip = hhf.host_hop_ip)
  -- where sl.email_subject is not null and igl.continent_code = 'AF'
  order by sl.id, jrh.received_header_index;
