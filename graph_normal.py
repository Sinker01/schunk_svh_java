import pandas as pd
import matplotlib.pyplot as plt

# Load the TSV file into a DataFrame
file_path = 'MIDDLE_PROXIMALlatenc-test1_1002ms.tsv'  # Replace with your actual file path
data = pd.read_csv(file_path, sep='\t')

# Create the plot
fig, ax1 = plt.subplots(figsize=(10, 6))

# Plot position on primary y-axis
ax1.plot(data['time[ms]'], data['position'], color='b', label='Position')
ax1.set_xlabel('Time [ms]')
ax1.set_ylabel('Position', color='b')
ax1.tick_params(axis='y', labelcolor='b')
ax1.grid(True)

# Create a second y-axis for current[mA]
ax2 = ax1.twinx()
ax2.plot(data['time[ms]'], data['current[mA]'], color='g', label='Current [mA]')
ax2.set_ylabel('Current [mA]', color='g')
ax2.tick_params(axis='y', labelcolor='g')

# Create a third y-axis for force[N]
ax3 = ax1.twinx()
ax3.spines['right'].set_position(('outward', 60))  # Offset the third y-axis
ax3.plot(data['time[ms]'], data['force[N]'], color='r', label='Force [N]')
ax3.set_ylabel('Force [N]', color='r')
ax3.tick_params(axis='y', labelcolor='r')

# Add legends
fig.legend(loc='upper right', bbox_to_anchor=(0.9, 0.9))

# Set the title
plt.title('Position, Current, and Force vs Time')

plt.show()
