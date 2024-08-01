import pandas as pd
import matplotlib.pyplot as plt
from matplotlib import pyplot as plt


# Load the TSV file into a DataFrame without headers
file_path = 'test_2.1/MIDDLE_PROXIMALlatenc-test_500868µs.tsv'  # Replace with your actual file path
data = pd.read_csv(file_path, sep='\t')
data = data[(data['time[µs]'] >= 480000) & (data['time[µs]'] <= 550000)]
# Create the plot
plt.figure(figsize=(10, 6))
# Scatter plot of position vs time
plt.scatter(data['time[µs]'], data['position'], color='b', label='Position', s=6)
# Mark the important value 1002 on the x-axis
important_time = 500868
plt.axvline(x=important_time, color='r', linestyle='--', label=f'Befehl zur Umkehr bei {important_time} µs')
# Add labels and title
plt.xlabel('Time [µs]')
plt.ylabel('Position')
plt.title('Position mit plötzlichen Befehl der Umkehr')
plt.legend()
plt.grid(True)
# Save the plot to a file
output_file = 'test_2.1/erster_latenztest.png'  # You can change the file name and format as needed
#plt.savefig(output_file)
# Show the plot
plt.show()
