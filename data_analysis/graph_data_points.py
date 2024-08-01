import pandas as pd
import matplotlib.pyplot as plt

# Load the TSV file into a DataFrame
file_path = 'MIDDLE_PROXIMALlatenc-test1_1002ms.tsv'  # Replace with your actual file path
data = pd.read_csv(file_path, sep='\t')

# Create the plot
plt.figure(figsize=(10, 6))

# Scatter plot of position vs time
plt.scatter(data['time[ms]'], data['position'], color='b', label='Position', s=0.1)

# Mark the important value 1002 on the x-axis
important_time = 1002
plt.axvline(x=important_time, color='r', linestyle='--', label=f'Time = {important_time} ms')

# Add labels and title
plt.xlabel('Time [ms]')
plt.ylabel('Position')
plt.title('Position vs Time')
plt.legend()
plt.grid(True)

# Save the plot to a file
output_file = '../position_vs_time_scatter.png'  # You can change the file name and format as needed
plt.savefig(output_file)

# Show the plot
plt.show()
