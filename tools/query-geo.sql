select sl.*, jrh.received_header_index, igl.*
  from spam_locator sl
  join spam_locator_received_headers jrh on (sl.message_id = jrh.spam_locator_message_message_id)
  join received_header rh on (jrh.received_headers_id = rh.id)
  join host_hop hh on (hh.id = rh.from_id)
  join ip_geo_location igl on (igl.ip = hh.host_hop_ip)
  where sl.email_subject is not null and igl.continent_code = 'SA'
  order by sl.email_subject, jrh.received_header_index;
