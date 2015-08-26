
wa = ARGV[0]
puts wa
inf = File.new("#{wa}.properties")
out = File.new("fields-#{wa}.xml", 'w')

out.puts '<fields>'
inf.each do |line|
  line.chomp!
  line.strip!
  if line.match('^\s*#') || line.empty? then
	puts "Skipping #{line}"
	next
  end
  id, val = line.split('=')
  start, len, ftype = val.split(',')
  ftype = 'REG' unless !ftype.nil?
  #xml = "<field id=\"#{id}\" start=\"#{start}\" length=\"#{len}\" type=\"#{ftype}\"><input alias=\"\" /><output alias=\"\" duplicates=\"\" /></field>"
  xml = "<field id=\"#{id}\" start=\"#{start}\" length=\"#{len}\" type=\"#{ftype}\"></field>"
  puts xml
  out.puts xml
end
inf.close
out.puts '</fields>'
out.close
